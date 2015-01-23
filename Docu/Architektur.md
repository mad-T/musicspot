# Architektur

Nach dem Modell des „Model-View-Controllers“ (siehe Abbildung) ist die Architektur der Webseite aufgebaut.

## Model-View-Controller:

![1.Model.png](/Docu/mvc-diagram1.png)

Die View beinhaltet hierbei alle HTML-Seiten, also das was schlussendlich im Browser des Users dargestellt wird. Im Model hingegen befindet sich die gesamte Logik der Seite. Das Model verwaltet unteranderem auch die Datenbank. Wird eine Funktion der Seite in Anspruch genommen, ruft der Controller(in dem Fall die Application-Klasse )die entsprechende Methode im Model auf und der Controller liefert dann das Ergebnis zurück an die View.

![1.Klassendiagramm.png](Kontakt)


Vorherige Seite: [Anforderungen](/Docu/Anforderungen.md) | Nächste Seite: [Technologien](/Docu/Technologien.md)
