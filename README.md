Laboratorium nr 6 - Języki Programowania - 3. semestr

Aplikacja działająca w środowisku rozproszonym, wykorzystuje do komunikacji gniazda TCP/IP. System, w którego skład wchodzą instancje klas uruchamianych równolegle (na jednym lub na kilku różnych komputerach). System ten jest symulatorem "Sieci boi sygnałowych" na wzór sieci, jaka pojawiła się w filmie „Battleship: Bitwa o Ziemię". Specyfikacja problemu:
- Na planszy o wymiarze 40 x 40 reprezentującej "morze" znajdują się sektory o wymiarze 5 x 5 ułożone w szachownicę (inaczej mówiąc: plansza to szachownica wymiarze 8 x 8, której "pola" to sektory rozmiaru 5 x 5).
- W środku sektorów znajdują się boje sygnałowe. Każda boja sygnałowa w określonych interwałach wysyła sygnał zawierający informację o poziomie morza do centrali.
- Po morzu pływają "statki". Podczas ruchu generują one fale. Fala generowana przez dany statek jest reprezentowana przez prostokąt 5x5 wypełniony wartościami jak niżej, który jest wycentrowany względem położenia tego statku na planszy (inaczej mówiąc statki poruszając się po planszy powodują przesuwanie się "wzgórków" reprezentowanych poniższymi wartościami)

01210

12321

23432

12321

01210

Zakładamy, że wzgórek istnieje w miejscu przebywania "statku" (wzgórek przesuwa się wraz ze "statkiem").
- Aby zasymulować zdarzenia zachodzące na morzu tworzona jest osobna aplikacja. Pełni ona rolę świata.
- Każdy statek wysyła do świata wiadomości z informacją o zamiarze wykonania ruchu (komendą "move", z parametrem określającym jeden z ośmiu możliwych kierunków). Zwrotnie statek otrzymuje informację, czy wykonanie komendy się udało, czy nie. Jeśli bowiem dojdzie do zderzenia się statków, statki pójdą na dno, jeśli zaś ruch wyprowadziłby statek poza obszar planszy, ruch jest zabroniony. Statki mogą przepływać przez pola zajmowane przez boje.
- Każdy statek może na żądanie otrzymać od "świata" informację o pozycjach innych statków (wysyłając komendę "scan", w wyniku której zwracana jest informacja o położeniu innych statków).
Podczas obsługi komendy "move" od danego statku "świat" informuje boje znajdujące się w pobliżu tegoż statku o jego pozycji. Boje na tej podstawie informują centralę i zostaje obliczony poziom morza na mapie.
- "Centrala" to osobna aplikacja (klasa z metodą main), która powinna oferować interfejs graficzny, na którym widać poziom morza "raportowany przez boje sygnałowe". W budowanym systemie uruchomiona jest tylko jedna jej instancja. 
- "Świat" to osobna aplikacja (klasa z metodą main), oferująca interfejs graficzny, na którym widać położenie statków. W budowanym systemie uruchomiona jest tylko jedna jej instancja.
- "Statek" to osobna aplikacja (klasa z metodą main), oferująca interfejs (może być prosty, okienkowy), na którym będzie można monitorować stan statku (czy pływa, jakie komendy wydaje i "co widzi"). W budowanym systemie uruchomionych będzie kilka jej instancji.
- "Boja" to osobna aplikacja (klasa z metodą main). W budowanym systemie uruchomionych jest wiele ich instancji
- Statki i boje pełnią role klientów, natomiast Świat i Centrala rolę serwerów
- Podsumowując, schemat komunikacji w budowanym systemie ma być następujący: „boja" komunikuje się z "centralą" oraz "światem" (otrzymując informacje o przepływających w pobliżu statkach); "statek" komunikuje się ze "światem" (wysyłając komendy "move" i "scan"). Liczba uruchamianych instancji statków może być dowolna. Liczba uruchamianych "boi" wynosi 64.
