/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsprojectjava;

/**
 *
 * @author hp
 */
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class DSprojectJAVA {

    public static class Post {
        private String body;
        private List<String> topics;

        public Post(String body) {
            this.body = body;
            this.topics = new ArrayList<>();
        }

        public void addTopic(String topic) {
            this.topics.add(topic);
        }

        public String getBody() {
            return body;
        }

        public List<String> getTopics() {
            return topics;
        }
    }

    public static class User {
        private int id;
        private String name;
        private List<Post> posts;
        private List<Integer> followers;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
            this.posts = new ArrayList<>();
            this.followers = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Post> getPosts() {
            return posts;
        }

        public List<Integer> getFollowers() {
            return followers;
        }

        public void addPost(Post post) {
            this.posts.add(post);
        }

        public void addFollower(int followerId) {
            this.followers.add(followerId);
        }
    }

    public static class Graph {
        private List<User> users;

        public Graph() {
            this.users = new ArrayList<>();
        }

        public void addUser(int id, String name) {
            users.add(new User(id, name));
        }

        public void addPost(int userId, Post post) {
            for (User user : users) {
                if (user.getId() == userId) {
                    user.addPost(post);
                    return;
                }
            }
        }

        public void addFollower(int followerId, int userId) {
            for (User user : users) {
                if (user.getId() == userId) {
                    user.addFollower(followerId);
                    return;
                }
            }
        }

        public void displayGraph() {
            for (User user : users) {
                System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());

                System.out.println("Posts:");
                for (Post post : user.getPosts()) {
                    System.out.println("  Body: " + post.getBody());
                    System.out.println("  Topics: " + String.join(" ", post.getTopics()));
                }

                System.out.println("Followers: " + user.getFollowers().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")));
                System.out.println();
            }
        }
        public List<User> getUsers() {
            return this.users;
        }

    }


    private static Graph graph = new Graph();
    public static Graph getGraph() {
        return graph;
    }

    private static String extractValue(String data, String openTag, String closeTag) {
        int start = data.indexOf(openTag);
        if (start == -1) return "";
        start += openTag.length();
        int end = data.indexOf(closeTag, start);
        if (end == -1) return "";
        return data.substring(start, end).trim();
    }

    public static void parseXML(String filename, Graph graph) {
        try {
            String content = new String(Files.readAllBytes(new File(filename).toPath()));

            // Step 1: Store users first
            List<Map.Entry<Integer, String>> usersData = new ArrayList<>();
            int userPos = 0;

            int usersPos = content.indexOf("<users>");
            if (usersPos != -1) {
                int usersEnd = content.indexOf("</users>", usersPos);
                String usersBlock = content.substring(usersPos, usersEnd);

                while ((userPos = usersBlock.indexOf("<user>", userPos)) != -1) {
                    int userEnd = usersBlock.indexOf("</user>", userPos);
                    if (userEnd == -1) break;

                    String userBlock = usersBlock.substring(userPos, userEnd);
                    userPos = userEnd + 7;

                    int id = Integer.parseInt(extractValue(userBlock, "<id>", "</id>"));
                    String name = extractValue(userBlock, "<name>", "</name>");

                    usersData.add(Map.entry(id, name));
                }

                for (Map.Entry<Integer, String> user : usersData) {
                    graph.addUser(user.getKey(), user.getValue());
                }
            }

            // Step 2: Add posts and followers
            userPos = 0;
            while ((userPos = content.indexOf("<user>", userPos)) != -1) {
                int userEnd = content.indexOf("</user>", userPos);
                if (userEnd == -1) break;

                String userBlock = content.substring(userPos, userEnd);
                userPos = userEnd + 7;

                int id = Integer.parseInt(extractValue(userBlock, "<id>", "</id>"));

                // Extract posts for this user
                int postsPos = userBlock.indexOf("<posts>");
                if (postsPos != -1) {
                    int postsEnd = userBlock.indexOf("</posts>", postsPos);
                    String postsBlock = userBlock.substring(postsPos, postsEnd);

                    int postPos = 0;
                    while ((postPos = postsBlock.indexOf("<post>", postPos)) != -1) {
                        int postEnd = postsBlock.indexOf("</post>", postPos);
                        if (postEnd == -1) break;

                        String postBlock = postsBlock.substring(postPos, postEnd);
                        postPos = postEnd + 7;

                        String body = extractValue(postBlock, "<body>", "</body>");
                        Post post = new Post(body);

                        // Extract topics
                        int topicsPos = postBlock.indexOf("<topics>");
                        if (topicsPos != -1) {
                            int topicsEnd = postBlock.indexOf("</topics>", topicsPos);
                            String topicsBlock = postBlock.substring(topicsPos, topicsEnd);

                            int topicPos = 0;
                            while ((topicPos = topicsBlock.indexOf("<topic>", topicPos)) != -1) {
                                int topicEnd = topicsBlock.indexOf("</topic>", topicPos);
                                if (topicEnd == -1) break;

                                String topic = topicsBlock.substring(topicPos + 7, topicEnd).trim();
                                post.addTopic(topic);
                                topicPos = topicEnd + 8;
                            }
                        }

                        graph.addPost(id, post);
                    }
                }

                // Extract followers for this user
                int followersPos = userBlock.indexOf("<followers>");
                if (followersPos != -1) {
                    int followersEnd = userBlock.indexOf("</followers>", followersPos);
                    String followersBlock = userBlock.substring(followersPos, followersEnd);

                    int followerPos = 0;
                    while ((followerPos = followersBlock.indexOf("<follower>", followerPos)) != -1) {
                        int followerEnd = followersBlock.indexOf("</follower>", followerPos);
                        if (followerEnd == -1) break;

                        String followerBlock = followersBlock.substring(followerPos, followerEnd);
                        int followerId = Integer.parseInt(extractValue(followerBlock, "<id>", "</id>"));
                        graph.addFollower(followerId, id);
                        followerPos = followerEnd + 11;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading XML file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        // Parse the XML file and populate the graph
        parseXML("sample.xml", graph);

        // Display the graph
        graph.displayGraph();
    }
}
