import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * SecureEnterpriseApp.java
 * Simple single-file enterprise-like app:
 * - Uses BufferedReader for input
 * - Password hashing with salt (SHA-256)
 * - Role-based access control (USER, ADMIN)
 * - Session tokens cached in LRU cache
 * - File-based persistence "users.db"
 * - Thread pool to run heavy tasks (simulate performance optimization)
 *
 * NOTE: This is an educational simplified example, not production ready.
 */
public class SecureEnterpriseApp {

    // ----------------------------
    // User Model
    // ----------------------------
    static class User {
        String username;
        String saltHex;        // hex-encoded salt
        String passwordHashHex;// hex-encoded salted hash
        String role;           // "USER" or "ADMIN"

        User(String username, String saltHex, String passwordHashHex, String role) {
            this.username = username;
            this.saltHex = saltHex;
            this.passwordHashHex = passwordHashHex;
            this.role = role;
        }

        String serialize() {
            // simple CSV (username|salt|hash|role)
            return username + "|" + saltHex + "|" + passwordHashHex + "|" + role;
        }

        static User deserialize(String line) {
            String[] parts = line.split("\\|");
            if (parts.length != 4) return null;
            return new User(parts[0], parts[1], parts[2], parts[3]);
        }
    }

    // ----------------------------
    // Simple file-based user store
    // ----------------------------
    static class UserStore {
        private final Path storeFile;

        UserStore(String filename) {
            this.storeFile = Paths.get(filename);
            try {
                if (!Files.exists(storeFile)) Files.createFile(storeFile);
            } catch (IOException e) {
                throw new RuntimeException("Unable to create store file: " + e.getMessage(), e);
            }
        }

        synchronized void addOrUpdate(User u) throws IOException {
            Map<String, User> all = loadAllMap();
            all.put(u.username, u);
            persistAll(all.values());
        }

        synchronized User get(String username) throws IOException {
            return loadAllMap().get(username);
        }

        synchronized List<User> listAll() throws IOException {
            return new ArrayList<>(loadAllMap().values());
        }

        private Map<String, User> loadAllMap() throws IOException {
            Map<String, User> m = new HashMap<>();
            List<String> lines = Files.readAllLines(storeFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                User u = User.deserialize(line.trim());
                if (u != null && !u.username.isEmpty()) m.put(u.username, u);
            }
            return m;
        }

        private void persistAll(Collection<User> users) throws IOException {
            List<String> lines = users.stream().map(User::serialize).collect(Collectors.toList());
            Files.write(storeFile, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    // ----------------------------
    // Auth utilities: hashing, salt, secure tokens
    // ----------------------------
    static class AuthUtil {
        private static final SecureRandom RNG = new SecureRandom();

        static String generateSaltHex(int bytes) {
            byte[] s = new byte[bytes];
            RNG.nextBytes(s);
            return bytesToHex(s);
        }

        static String hashPassword(String plainPassword, String saltHex) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(hexToBytes(saltHex));
                md.update(plainPassword.getBytes(StandardCharsets.UTF_8));
                byte[] digest = md.digest();
                return bytesToHex(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        static String generateToken() {
            byte[] t = new byte[24];
            RNG.nextBytes(t);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(t);
        }

        // lightweight HMAC for token binding demonstration (not used for JWT here)
        static String hmacSHA256(String key, String data) {
            try {
                SecretKeySpec sk = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(sk);
                byte[] out = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
                return bytesToHex(out);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        }

        static byte[] hexToBytes(String s) {
            int len = s.length();
            byte[] out = new byte[len / 2];
            for (int i = 0; i < len; i += 2) out[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
            return out;
        }
    }

    // ----------------------------
    // LRU Cache for sessions (performance optimization)
    // ----------------------------
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int maxEntries;
        LRUCache(int maxEntries) {
            super(16, 0.75f, true);
            this.maxEntries = maxEntries;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxEntries;
        }
    }

    // ----------------------------
    // AuthService: register, login, session management
    // ----------------------------
    static class AuthService {
        private final UserStore store;
        private final Map<String, String> tokenToUser = Collections.synchronizedMap(new LRUCache<>(1000)); // token -> username
        private final Map<String, Long> tokenExpiry = new ConcurrentHashMap<>();
        private final long sessionTtlMillis = 30 * 60 * 1000; // 30 minutes

        AuthService(UserStore store) {
            this.store = store;
        }

        // registration
        boolean register(String username, String plainPassword, String role) throws IOException {
            if (store.get(username) != null) return false;
            String salt = AuthUtil.generateSaltHex(16);
            String hash = AuthUtil.hashPassword(plainPassword, salt);
            User u = new User(username, salt, hash, role);
            store.addOrUpdate(u);
            return true;
        }

        // login -> returns token or null
        String login(String username, String plainPassword) throws IOException {
            User u = store.get(username);
            if (u == null) return null;
            String attempted = AuthUtil.hashPassword(plainPassword, u.saltHex);
            if (!attempted.equals(u.passwordHashHex)) return null;
            String token = AuthUtil.generateToken();
            tokenToUser.put(token, username);
            tokenExpiry.put(token, System.currentTimeMillis() + sessionTtlMillis);
            return token;
        }

        // validate token and extend TTL (sliding expiration)
        String validateToken(String token) {
            String username = tokenToUser.get(token);
            if (username == null) return null;
            Long expiry = tokenExpiry.get(token);
            if (expiry == null || expiry < System.currentTimeMillis()) {
                tokenToUser.remove(token);
                tokenExpiry.remove(token);
                return null;
            }
            tokenExpiry.put(token, System.currentTimeMillis() + sessionTtlMillis); // sliding
            return username;
        }

        void logout(String token) {
            tokenToUser.remove(token);
            tokenExpiry.remove(token);
        }

        List<String> activeSessions() {
            return new ArrayList<>(tokenToUser.keySet());
        }
    }

    // ----------------------------
    // RBAC service: checks role
    // ----------------------------
    static class RBACService {
        private final UserStore store;
        RBACService(UserStore store) { this.store = store; }
        boolean hasRole(String username, String requiredRole) throws IOException {
            User u = store.get(username);
            if (u == null) return false;
            return u.role.equals(requiredRole);
        }
    }

    // ----------------------------
    // Simulated heavy work using thread pool
    // ----------------------------
    static class WorkerPool {
        private final ExecutorService pool;
        WorkerPool(int threads) {
            pool = Executors.newFixedThreadPool(threads);
        }
        void submitHeavyJob(String jobName, int complexity) {
            pool.submit(() -> {
                System.out.println("[Worker] Starting job: " + jobName + " complexity=" + complexity);
                // simulate CPU-bound or I/O-bound work
                try {
                    // do incremental work and sleep to simulate varying operations
                    for (int i = 0; i < complexity; i++) {
                        // small CPU-bound loop
                        double x = 0;
                        for (int j = 0; j < 100000; j++) x += Math.sin(j) * Math.cos(j);
                        if (i % 2 == 0) Thread.sleep(100);
                    }
                } catch (InterruptedException ignored) {}
                System.out.println("[Worker] Completed job: " + jobName);
            });
        }
        void shutdown() {
            pool.shutdown();
            try { pool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException ignored) {}
        }
    }

    // ----------------------------
    // The CLI main application using BufferedReader
    // ----------------------------
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserStore store = new UserStore("users.db");
        AuthService auth = new AuthService(store);
        RBACService rbac = new RBACService(store);
        WorkerPool workers = new WorkerPool(Runtime.getRuntime().availableProcessors());

        // Ensure at least one admin exists
        try {
            if (store.get("admin") == null) {
                auth.register("admin", "Admin@123", "ADMIN");
                System.out.println("Default admin user created -> username: admin, password: Admin@123");
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize store: " + e.getMessage());
        }

        String currentToken = null;

        loop:
        while (true) {
            try {
                System.out.println("\n=== Sunrise Enterprises – Secure App (Demo) ===");
                System.out.println("1) Register");
                System.out.println("2) Login");
                System.out.println("3) Logout");
                System.out.println("4) View My Profile (protected)");
                System.out.println("5) List All Users (admin-only)");
                System.out.println("6) Submit Heavy Job (background)");
                System.out.println("7) Show Active Sessions (admin-only)");
                System.out.println("8) Exit");
                System.out.print("Choose >> ");
                String choice = br.readLine();
                if (choice == null) break;

                switch (choice.trim()) {
                    case "1": // Register
                        System.out.print("Enter username: ");
                        String ru = br.readLine().trim();
                        System.out.print("Enter password: ");
                        String rp = br.readLine().trim();
                        System.out.print("Role (USER or ADMIN): ");
                        String rrole = br.readLine().trim().toUpperCase();
                        if (!rrole.equals("USER") && !rrole.equals("ADMIN")) rrole = "USER";
                        boolean ok = auth.register(ru, rp, rrole);
                        System.out.println(ok ? "Registered successfully." : "User already exists.");
                        break;

                    case "2": // Login
                        System.out.print("Username: ");
                        String lu = br.readLine().trim();
                        System.out.print("Password: ");
                        String lp = br.readLine().trim();
                        String token = auth.login(lu, lp);
                        if (token == null) {
                            System.out.println("Invalid credentials.");
                        } else {
                            currentToken = token;
                            System.out.println("Login success. Session token: " + token);
                        }
                        break;

                    case "3": // Logout
                        if (currentToken != null) {
                            auth.logout(currentToken);
                            currentToken = null;
                            System.out.println("Logged out.");
                        } else System.out.println("No active session.");
                        break;

                    case "4": // View My Profile
                        if (currentToken == null) { System.out.println("Login first."); break; }
                        String username = auth.validateToken(currentToken);
                        if (username == null) { currentToken = null; System.out.println("Session expired or invalid. Please login again."); break; }
                        User me = store.get(username);
                        System.out.println("=== Profile ===");
                        System.out.println("Username: " + me.username);
                        System.out.println("Role: " + me.role);
                        break;

                    case "5": // List all users (admin)
                        if (currentToken == null) { System.out.println("Login first."); break; }
                        String uname = auth.validateToken(currentToken);
                        if (uname == null) { currentToken = null; System.out.println("Session expired."); break; }
                        if (!rbac.hasRole(uname, "ADMIN")) { System.out.println("Access denied. Admins only."); break; }
                        List<User> users = store.listAll();
                        System.out.println("All users:");
                        for (User u : users) System.out.println("- " + u.username + " [" + u.role + "]");
                        break;

                    case "6": // background job
                        System.out.print("Enter job name: ");
                        String jn = br.readLine().trim();
                        System.out.print("Complexity (1-10): ");
                        String compStr = br.readLine().trim();
                        int complexity = 3;
                        try { complexity = Math.max(1, Math.min(10, Integer.parseInt(compStr))); } catch (Exception ignored) {}
                        workers.submitHeavyJob(jn, complexity);
                        System.out.println("Job submitted to worker pool.");
                        break;

                    case "7": // show active sessions (admin)
                        if (currentToken == null) { System.out.println("Login first."); break; }
                        String au = auth.validateToken(currentToken);
                        if (au == null) { currentToken = null; System.out.println("Session expired."); break; }
                        if (!rbac.hasRole(au, "ADMIN")) { System.out.println("Access denied."); break; }
                        List<String> sess = auth.activeSessions();
                        System.out.println("Active tokens (count=" + sess.size() + "):");
                        for (String s : sess) System.out.println(s);
                        break;

                    case "8":
                        System.out.println("Shutting down...");
                        break loop;

                    default:
                        System.out.println("Unknown option.");
                }
            } catch (IOException e) {
                System.err.println("I/O error: " + e.getMessage());
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }

        workers.shutdown();
        System.out.println("Exited.");
    }
}
