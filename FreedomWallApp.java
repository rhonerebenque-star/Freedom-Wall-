import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FreedomWallApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Display the Freedom Wall header
                System.out.println("\n=== PAHINA'T GUNITA ===");
                // Prompt to press Enter
                scanner.nextLine();  // Wait for user to press Enter
                // Now ask if they want to continue
                System.out.println("Do you want to continue? YES or NO: ");
                System.out.print("ANSWER: ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("yes")) {
                    // Launch the Freedom Wall application
                    PahinatGunita app = new PahinatGunita();
                    app.load();
                    app.runMenu();
                    // After saving and exiting, prompt to go back
                    System.out.println("\nPress Enter to go back to the first place.");
                    scanner.nextLine();
                } else {
                    System.out.println("\nThank you!, Feel free to comeback to Express how you feel.");
                    break;
                }
            }
        }
    }
}

class PahinatGunita {
    private static final String DATA_FILE = "Freedom Wall_posts.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private final List<Post> posts = new ArrayList<>();
    private int nextId = 1;

    public void load() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // parse line
                // id|type|timestamp|content (pipes and backslashes in content escaped)
                List<String> parts = splitEscaped(line, '|');
                if (parts.size() < 4) continue;
                int id = Integer.parseInt(parts.get(0));
                String type = parts.get(1);
                LocalDateTime ts = LocalDateTime.parse(parts.get(2));
                String content = parts.get(3).replace("\\|", "|").replace("\\\\", "\\");
                Post p = switch (type) {
                    case "Letter" -> new Letter(id, content, ts);
                    case "Confession" -> new Confession(id, content, ts);
                    case "Rant" -> new Rant(id, content, ts);
                    default -> new Letter(id, content, ts);
                };
                posts.add(p);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (Exception e) {
            System.out.println("\nFailed to load posts: " + e.getMessage());
        }
    }
    
    public void runMenu() {
        while (true) {
            System.out.println("\n1) Create a Post");
            System.out.println("2) View All Posts (admin)");
            System.out.println("3) Search Posts by Keyword");
            System.out.println("4) Delete a Post (admin)");
            System.out.println("5) Save & Exit");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createPost();
                case "2" -> viewAllPosts();
                case "3" -> searchPosts();
                case "4" -> deletePost();
                case "5" -> {
                    save();
                    System.out.println("\nThank you for opening up. Whether your words come from joy, pain, confusion, or hope, \nthis space welcomes them all. Your story is safe and secured at \"Pahina't Guinita\". goodbye!.");
                    return;
                }
                default -> System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    private void createPost() {
        System.out.println("\nSelect Type:");
        System.out.println("\n1) Letter");
        System.out.println("2) Confession");
        System.out.println("3) Rant");
        System.out.print("\nChoice: ");
        String type = scanner.nextLine().trim();

        System.out.println("\nEnter your post content (Press Enter on an empty line to finish.):");
        StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            sb.append(line).append(System.lineSeparator());
        }
        String content = sb.toString().trim();
        
        if (content.isEmpty()) {
            System.out.println("\nEmpty content. Post cancelled.");
            return;
        }

        Post post;
        int id = nextId++;
        switch (type) {
            case "1" -> post = new Letter(id, content);
            case "2" -> post = new Confession(id, content);
            case "3" -> post = new Rant(id, content);
            default -> {
                System.out.println("\nUnknown type. Creating as Letter by default.");
                post = new Letter(id, content);
            }
        }

        posts.add(post);
        System.out.println("\nPosted anonymously as a " + post.getType() + " (id = " + post.getId() + ").");
    }

    private void viewAllPosts() {
        System.out.print("\nEnter admin password: ");
        String pw = scanner.nextLine();
        // simple admin check for demo purposes
        if (!"ARIZONA_B".equals(pw)) {
            System.out.println("Incorrect password.");
            return;
        }
        
        if (posts.isEmpty()) {
            System.out.println("No posts yet.");
            return;
        }

        // show newest first
        posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .forEach(Post::displaySummary);

        System.out.print("\nWould you like to read a specific post? (ID No. or Enter to go back): ");
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            try {
                int id = Integer.parseInt(line);
                posts.stream().filter(p -> p.getId() == id).findFirst()
                        .ifPresentOrElse(Post::displayFull, () -> System.out.println("Post not found."));
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID.");
            }
        }
    }

    private void searchPosts() {
        System.out.print("Enter keyword to search: ");
        String kw = scanner.nextLine().trim().toLowerCase();
        if (kw.isEmpty()) { System.out.println("No keyword."); return; }

        List<Post> found = new ArrayList<>();
        for (Post p : posts) {
            if (p.getContent().toLowerCase().contains(kw)) found.add(p);
        }

        if (found.isEmpty()) {
            System.out.println("No posts matched \"" + kw + "\".");
            return;
        }

        System.out.println("Found " + found.size() + " posts:");
        found.stream().sorted(Comparator.comparing(Post::getTimestamp).reversed()).forEach(Post::displaySummary);
    }

   private void deletePost() {
    System.out.print("\nEnter admin password: ");
    String pw = scanner.nextLine();
    // simple admin check for demo purposes
    if (!"ARIZONA_B".equals(pw)) {
        System.out.println("Incorrect password.");
        return;
    }
    System.out.println("\n1) Delete a specific post");
    System.out.println("2) Delete all posts");
    System.out.print("Choose an option: ");
    String choice = scanner.nextLine().trim();
    switch (choice) {
        case "1" -> {
            System.out.print("Enter post id to delete: ");
            try {
                int id = Integer.parseInt(scanner.nextLine().trim());
                boolean removed = posts.removeIf(p -> p.getId() == id);
                System.out.println(removed ? "Post deleted." : "Post with that id not found.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid id.");
            }
        }
        case "2" -> {
            posts.clear();
            System.out.println("All posts deleted.");
        }
        default -> System.out.println("Invalid option.");
    }
}


    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Post p : posts) {
                // format: id|type|timestamp|content (pipe-separated, content has pipes and backslashes escaped)
                String safe = p.getContent().replace("\\", "\\\\").replace("|", "\\|");
                pw.println(p.getId() + "|" + p.getType() + "|" + p.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "|" + safe);
            }
        } catch (IOException e) {
            System.out.println("Failed to save posts: " + e.getMessage());
        }
    }

    // utility to split by separator but allow escaping with backslash
    private static List<String> splitEscaped(String s, char sep) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean esc = false;
        for (char c : s.toCharArray()) {
            if (esc) { cur.append(c); esc = false; continue; }
            if (c == '\\') { esc = true; continue; }
            if (c == sep) { out.add(cur.toString()); cur.setLength(0); continue; }
            cur.append(c);
        }
        out.add(cur.toString());
        return out;
    }
}

// ========== Abstraction & Inheritance ==========
abstract class Post {
    private final int id; // encapsulated (private)
    private String content; // encapsulated with getter/setter
    private final LocalDateTime timestamp; // encapsulated

    protected Post(int id, String content) {
        this(id, content, LocalDateTime.now());
    }

    protected Post(int id, String content, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Encapsulation: controlled access
    public int getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Abstraction: concrete subclasses must describe their type
    public abstract String getType();

    // Polymorphism: each subclass can display itself differently
    public void displaySummary() {
        System.out.println("\n[" + getType() + "] (id=" + id + ") " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" + preview());
    }

    public void displayFull() {
        System.out.println("\n----- " + getType() + " (id = " + id + ") -----");
        System.out.println("Posted: " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println(getContent());
        System.out.println("---------------------------\n");
    }

    private String preview() {
        String c = getContent();
        return c.length() > 60 ? c.substring(0, 57) + "..." : c;
    }
}

class Letter extends Post {
    public Letter(int id, String content) { super(id, content); }
    public Letter(int id, String content, LocalDateTime ts) { super(id, content, ts); }
    @Override
    public String getType() { return "Letter"; }
}

class Confession extends Post {
    public Confession(int id, String content) { super(id, content); }
    public Confession(int id, String content, LocalDateTime ts) { super(id, content, ts); }
    @Override
    public String getType() { return "Confession"; }
}

class Rant extends Post {
    public Rant(int id, String content) { super(id, content); }
    public Rant(int id, String content, LocalDateTime ts) { super(id, content, ts); }
    @Override
    public String getType() { return "Rant"; }
}
