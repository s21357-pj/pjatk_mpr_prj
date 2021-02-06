package org.pjatktest;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public State currentState = new State();
    public List<HistoryBox> stateHistory = new ArrayList<HistoryBox>();
    String name;
    String surname;
    String personalId;

    public Application(String name, String surname, String personalId) {
        this.name = name;
        this.surname = surname;
        this.personalId = personalId;
    }

    public List<HistoryBox> getHistory() {
        return this.stateHistory;
    }
    public static class Document {
        private String type, description, issueDate;
        public Document (String type, String description, String issueDate) {
          this.type = type;
          this.description = description;
          this.issueDate = issueDate;
       }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getIssueDate() {
            return issueDate;
        }
    }

    public static class Comment {
        String comment;
        public Comment (String x) {
            this.comment = x;
        }
        public Comment () {
        }
        public void setComment(String x) {
            this.comment = x;
        }
        public String getComment() {
            return this.comment;
        }

    }

    public class HistoryBox {
        ApplicationState state;
        Comment comment;
        public HistoryBox(ApplicationState a, Comment x) {
            this.state = a;
            this.comment = x;
        }

        public String getState() {
            return state.toString();
        }

        public String getComment() {
            return comment.getComment();
        }
    }
    public class State {
        private ApplicationState appState = ApplicationState.NEW_APPLICATION;
        public List<Document> documentsList = new ArrayList<>();
        private Comment comment = new Comment();
        public State startProcess(String x) {
            appState = appState.startProcess(comment, x);
            stateHistory.add(new HistoryBox(this.appState, new Comment(this.comment.getComment())));
            return this;
        }

        public State close(String x) {
            appState = appState.close(comment, x);
            stateHistory.add(new HistoryBox(this.appState, this.comment));
            return this;
        }

        public State toFulfillment(String x) {
            appState = appState.toFulfillment(comment, x);
            stateHistory.add(new HistoryBox(this.appState, new Comment(this.comment.getComment())));
            return this;
        }

        public String getComment() {
            return appState.getComment(comment);
        }

        public void addDocument(String type, String description, String issueDate) {
            appState.addDocument(documentsList, type, description, issueDate);
        }

        public List<Application.Document> getDocuments() {
            return appState.getDocuments(documentsList);
        }
        public State accept(String x) {
            appState = appState.accept(comment, x);
            stateHistory.add(new HistoryBox(this.appState, new Comment(this.comment.getComment())));
            return this;
        }
        public State verify(String x) {
            appState = appState.verify(comment, x);
            stateHistory.add(new HistoryBox(this.appState, new Comment(this.comment.getComment())));
            return this;
        }
        public State toReady(String x) {
            appState = appState.toReady(comment, x);
            stateHistory.add(new HistoryBox(this.appState, new Comment(this.comment.getComment())));
            return this;
        }
        @Override
        public String toString() {
            return appState.toString();
        }

    }
    public enum ApplicationState implements ApplicationStateInterface {
        NEW_APPLICATION {
            public ApplicationState startProcess(Comment out, String in) {
                out.setComment(in);
                return VERIFICATION;
            }
            public void addDocument(List<Application.Document> List, String type, String description, String issueDate) {
                List.add(new Document(type, description, issueDate));
            }
            public List<Application.Document> getDocuments(List<Application.Document> List) {
                return List;
            }
        },
        VERIFICATION {
            public ApplicationState toFulfillment(Comment out, String in) {
                out.setComment(in);
                return FULFILLMENT;
            }
            public ApplicationState accept(Comment out, String in) {
                out.setComment(in);
                return PRINTING;
            }
            public String getComment(Comment out) {
                return out.getComment();
            }
            public List<Application.Document> getDocuments(List<Application.Document> List) {
                return List;
            }
        },
        FULFILLMENT {
            public ApplicationState verify(Comment out, String in) {
                out.setComment(in);
                return VERIFICATION;
            }
            public ApplicationState close(Comment out, String in) {
                out.setComment(in);
                return CLOSED_APPLICATION;
            }
            public String getComment(Comment out) {
                return out.getComment();
            }
            public void addDocument(List<Application.Document> List, String type, String description, String issueDate) {
                List.add(new Document(type, description, issueDate));
            }
            public List<Application.Document> getDocuments(List<Application.Document> List) {
                return List;
            }
        },
        PRINTING {
            public ApplicationState toReady(Comment out, String in) {
                out.setComment(in);
                return READY_APPLICATION;
            }
            public String getComment(Comment out) {
                return out.getComment();
            }
        },
        READY_APPLICATION {
            public ApplicationState close(Comment out, String in) {
                out.setComment(in);
                return CLOSED_APPLICATION;
            }
            public String getComment(Comment out) {
                return out.getComment();
            }
        },
        CLOSED_APPLICATION {
            public String getComment(Comment out) {
                return out.getComment();
            }
        }

    }
}