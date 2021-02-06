# pjatk_mpr_prj
![Scheme](/docs/scheme.png)

Wzorzec "State machine" został wybrany ponieważ najbardziej pasuje do tego zadania.
Wniosek przy procesowaniu może mieć tylko 1 konkretny stan 
i stan musi być zmieniany w przewidywalny sposób.
Więc według schematu został zaimplementowany behawioralny ENUM ApplicationState
oraz interfejs w celu zapewnienia wyjątków.
Klasa State zmienia stan wniosku oraz zapisuje zmiany do logu.
Nowy wniosek reprezentuje klasa Application.


# Przykłady użycia:

### Utworzenie wniosku 
Application testApp = new Application("TestName", "TestSurname", "9001249012903");

### Przetwarzanie wniosku
assertSame("VERIFICATION", testApp.currentState.startProcess("comment").toString());
assertSame("FULFILLMENT", testApp.currentState.toFulfillment("comment").toString());
assertSame("VERIFICATION", testApp.currentState.verify("comment").toString());
assertSame("PRINTING", testApp.currentState.accept("comment").toString());
assertSame("READY_APPLICATION", testApp.currentState.toReady("comment").toString());
assertSame("CLOSED_APPLICATION", testApp.currentState.close("comment").toString());

### Sprawdzenie na jakim etapie jest wniosek
testApp.currentState.toString();

### Historia stanów
testApp.getHistory();

### Dodanie dokumentów
testApp.currentState.addDocument("birth certificate", "issued by EU", "1990-02-22");

### Pobieranie dukumentów
testApp.currentState.getDocuments();
