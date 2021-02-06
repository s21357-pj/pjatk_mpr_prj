package org.pjatktest;

import java.util.List;

public interface ApplicationStateInterface {

    default Application.ApplicationState startProcess(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }
    default Application.ApplicationState close(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }
    default Application.ApplicationState toFulfillment(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }
    default void addDocument(List<Application.Document> List, String type, String description, String issueDate){
        throw new RuntimeException("Unsupported method in this state");
    }

    default String getComment(Application.Comment x){
        throw new RuntimeException("Unsupported method in this state");
    }

    default List<Application.Document> getDocuments(List<Application.Document> List){
        throw new RuntimeException("Unsupported method in this state");
    }
    default Application.ApplicationState accept(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }
    default Application.ApplicationState verify(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }
    default Application.ApplicationState toReady(Application.Comment comment, String x){
        throw new RuntimeException("Unsupported method in this state");
    }

}
