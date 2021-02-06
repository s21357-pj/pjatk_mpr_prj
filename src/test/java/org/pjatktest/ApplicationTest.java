package org.pjatktest;


import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertSame;

public class ApplicationTest {


    @Test
    public void test_addedDocument_saved() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        assertSame("birth certificate", testApp.currentState.getDocuments().get(0).getType());
        assertSame("issued by EU", testApp.currentState.getDocuments().get(0).getDescription());
        assertSame("1990-02-22", testApp.currentState.getDocuments().get(0).getIssueDate());
    }

    @Test
    public void test_added2Documents_saved() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        testApp.currentState.addDocument("photo", "color", "2020-01-22");
        assertSame("birth certificate", testApp.currentState.getDocuments().get(0).getType());
        assertSame("issued by EU", testApp.currentState.getDocuments().get(0).getDescription());
        assertSame("1990-02-22", testApp.currentState.getDocuments().get(0).getIssueDate());
        assertSame("photo", testApp.currentState.getDocuments().get(1).getType());
        assertSame("color", testApp.currentState.getDocuments().get(1).getDescription());
        assertSame("2020-01-22", testApp.currentState.getDocuments().get(1).getIssueDate());
    }

    @Test
    public void test_fulfillmentMessage_saved() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("no photo").toString());
        assertSame("no photo", testApp.currentState.getComment());
        testApp.currentState.verify("");
        testApp.currentState.toFulfillment("uncolored photo");
        assertSame("uncolored photo", testApp.currentState.getComment());
    }

    @Test
    public void test_inFulfillmentState_getDocument() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("no photo").toString());
        assertSame("birth certificate", testApp.currentState.getDocuments().get(0).getType());
        assertSame("issued by EU", testApp.currentState.getDocuments().get(0).getDescription());
        assertSame("1990-02-22", testApp.currentState.getDocuments().get(0).getIssueDate());
    }
    @Test
    public void test_inFulfillmentState_close() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("no photo").toString());
        assertSame("CLOSED_APPLICATION", testApp.currentState.close("").toString());
    }
    @Test
    public void test_fullFlow_ok() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        assertSame("VERIFICATION", testApp.currentState.verify("").toString());
        assertSame("PRINTING", testApp.currentState.accept("").toString());
        assertSame("READY_APPLICATION", testApp.currentState.toReady("").toString());
        assertSame("CLOSED_APPLICATION", testApp.currentState.close("").toString());
    }

    @Test
    public void test_getComment_atReadyState() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        assertSame("VERIFICATION", testApp.currentState.verify("").toString());
        assertSame("PRINTING", testApp.currentState.accept("").toString());
        assertSame("READY_APPLICATION", testApp.currentState.toReady("Printed").toString());
        assertSame("Printed", testApp.currentState.getComment());
    }
    @Test
    public void test_getComment_atPrintingState() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        assertSame("VERIFICATION", testApp.currentState.verify("").toString());
        assertSame("PRINTING", testApp.currentState.accept("Preparing").toString());
        assertSame("Preparing", testApp.currentState.getComment());
    }
    @Test
    public void test_getComment_atVerificationState() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        assertSame("VERIFICATION", testApp.currentState.verify("ok").toString());
        assertSame("ok", testApp.currentState.getComment());
    }
    @Test
    public void test_getComment_atCloseState() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        assertSame("VERIFICATION", testApp.currentState.verify("").toString());
        assertSame("PRINTING", testApp.currentState.accept("").toString());
        assertSame("READY_APPLICATION", testApp.currentState.toReady("").toString());
        assertSame("CLOSED_APPLICATION", testApp.currentState.close("issued").toString());
        assertSame("issued", testApp.currentState.getComment());
    }

    @Test
    public void test_fullFlow_history() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        String[] assertStates = new String[] {"VERIFICATION" , "FULFILLMENT", "VERIFICATION", "PRINTING",
                "READY_APPLICATION", "CLOSED_APPLICATION"};
        assertSame(assertStates[0], testApp.currentState.startProcess("a").toString());
        assertSame(assertStates[1], testApp.currentState.toFulfillment("b").toString());
        assertSame(assertStates[2], testApp.currentState.verify("c").toString());
        assertSame(assertStates[3], testApp.currentState.accept("dd").toString());
        assertSame(assertStates[4], testApp.currentState.toReady("ee").toString());
        assertSame(assertStates[5], testApp.currentState.close("ff").toString());

        for (int x = 0; x<assertStates.length; x++) {
            assertSame(testApp.getHistory().get(x).getState(), assertStates[x]);
        }
        assertSame("a", testApp.getHistory().get(0).getComment());
        assertSame("b", testApp.getHistory().get(1).getComment());
        assertSame("c", testApp.getHistory().get(2).getComment());
        assertSame("dd", testApp.getHistory().get(3).getComment());
        assertSame("ee", testApp.getHistory().get(4).getComment());
        assertSame("ff", testApp.getHistory().get(5).getComment());
    }
    @Test
    public void test_inFulfillmentState_addDocument() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("FULFILLMENT", testApp.currentState.toFulfillment("").toString());
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        assertSame("birth certificate", testApp.currentState.getDocuments().get(0).getType());
        assertSame("issued by EU", testApp.currentState.getDocuments().get(0).getDescription());
        assertSame("1990-02-22", testApp.currentState.getDocuments().get(0).getIssueDate());
    }

    @Test
    public void test_inVerificationState_getDocument() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");
        assertSame("VERIFICATION", testApp.currentState.startProcess("").toString());
        assertSame("birth certificate", testApp.currentState.getDocuments().get(0).getType());
        assertSame("issued by EU", testApp.currentState.getDocuments().get(0).getDescription());
        assertSame("1990-02-22", testApp.currentState.getDocuments().get(0).getIssueDate());
    }
    @Test
    public void test_closeInNewApplicationState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.close("")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_startProcessInFulfillmentState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.startProcess("").toFulfillment("test").startProcess("")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_toFulfilmentInClosedState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.startProcess("").toFulfillment("test").close("").toFulfillment("test")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_acceptInFulfilmentState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.startProcess("").toFulfillment("test").accept("")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_addDocumentInPrintingState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.startProcess("").accept("").addDocument("a", "b", "2020-11-11")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_getCommentInNewApplicationState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.getComment()).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_getDocumentsInPrintingState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.startProcess("").accept("").getDocuments()).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_verifyInNewApplicationState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.verify("")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
    @Test
    public void test_toReadyInNewApplicationState_exception() {
        Application testApp = new Application("TestName", "TestSurname", "9001249012903");
        assertThatThrownBy(() -> testApp.currentState.toReady("")).isInstanceOf(RuntimeException.class).hasMessage("Unsupported method in this state") ;
    }
}
