// Copyright 324CC Miron Andreea-Cristiana

Programarea orientata pe obiecte

Tema: Catalog electronic

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	Ganduri dinainte de a citi acest ReadMe: O sa presupun ca se doreste
o explicatie cat mai restransa pentru aceasta tema. Deobicei fac Readme -uri
detaliate, insa nici voi nu cred ca aveti timp de corectat prea mult.

	Timp de implementare: 3 zile jumate
	
	Feedback: Mi se pare foarte dubioasa organizarea claselor, dar am 
presupus ca sunt puse asa pe scop didactic. Daca era sa facem catalogul pe
freestyle, clar vedeati totul complet diferit:)) dar ne adaptam ca oriunde. Pot
spune ca am fixat multe notiuni de oop pe aceasta tema. Very nice:) 

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Implementare
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Catalog

	Catalogul pe care dorim sa il facem electronic este ilustrata intr-o
clasa numita "Catalog", ce poate fi instantiata o data datorita sablonului de
proiectare Singleton. Astfel, pentru a putea obtine catalogul e nevoie sa 
apelezi "getCatalog". Aceasta clasa are urmatorii membri:
	- catalog : instanta de care vorbeam mai devreme
	- courses (ArrayList) : o lista de cursuri aflate in catalog
	- observers (ArrayList) : o lista de observatori ce este formata din
	  parinti,care s-au "abonat" la noutatile copilului lor

	Metodele clasei sunt urmatoarele:
	- addCourse, removeCourse : care adauga sau elimina un curs
	- addObserver, removeObserver, notifyObservers: care realizeaza 
	operatii legate de observatori
	*Aceasta clasa totodata face parte dintr-un sablon Observer, astfel
	ca implementeaza interfata Subject*
	- getCourse : primeste ca parametru numele cursului si returneaza 
	cursul corespunzator
	- getStudents: returneaza o lista cu toti studentii
	- verifyStudent, verifyAssistant, verifyTeacher, verifyParent: cauta
	peste tot daca user-ul respectiv a mai fost instantiat, pentru a nu
	avea instantieri diferite, ceea ce poate duce la multe erori in date

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
User - Student, Teacher, Assistant, Parent

	Clasa User a fost data in cerinta temei, insa singurele adaugari reali-
zate de mine au fost getteri si settere, o verificare de egalitate de useri 
dupa nume (acesta fiind principalul criteriu de diferentiere inteles si de pe 
forum) prin metoda equalsUser si o metoda abstracta ce va fi implementata
in toate clasele ce vor mostenii User, care returneaza cursurile la care este
asignat acel user(in cazul parintelui, cursurile la care e asignat copilul lui).

	Clasele mostenitoare care fac parte din alte sabloane de proiectare vor
fi explicate in momentul discutarii sabloanelor.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Grade
	
	Aceasta clasa respecta in intregime cerinta, neadaugand alte metode,
insa foarte dubioasa din punctul meu de vedere. Grade-ul ar fi primul lucru
pe care l-as schimba dupa predarea temei. Chiar sunt curioasa la ce v-ati gandit
cand ii creati structura.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Group

	Cred ca regret ce am zis mai devreme, Group-ul a fost cel mai dubios.
Acest Group a fost gandit ceva combinatie dubiosica american-roman. De exemplu,
un grup random format din studenti trebuie instantiat de mai multe ori, pentru
ca la fiecare curs are un asistent diferit. Deci din punct de vedere al logicii
instantiam acelas arraylist de mai multe ori doar pentru un asistent. Foarta
ambiguu. 
	Revenind, nici aici nu sunt alte metode noi implementate de mine si
respecta cerinta problemei.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Course

	Pentru aceasta clasa a fost implementata strategia Builder, unde am
considerat ca si campuri obligatorii numele cursului, profesorul si strategia.
Metoda de build am lasat-o abstracta, deoarece clasa Course este tot abstracta
si nu poate fi instantiata, in schimb clasele care mostenesc Course da. Asa ca,
metoda build este implementata in clasele interne PartialCourseBuilder si
ExamCourseBuilder.
	Am implementat getter si setter la toate membrele clasei si metodele
descrise in enunt. 
	Pentru a realiza back-up la note, am respectat sablonul Memento pentru
care am creat o clasa interna SnapShot ce are ca membru un arraylist de note
la care s-a dat back-up. Astfel, pentru a face back-up notelor dintr-un curs
apelam metoda makeBackup si seteaza acel arraylist, iar pentru a reveni la ce 
aveam inainte, apelam metoda undo din clasa Course.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Cum alege un profesor cel mai bun student la cursul sau???
	
	Raspunsul e simplu: sablonul de proiectare Strategy! S-a creat o 
interfata Strategy ce are ca metoda abstracta getBestScore, care v-a fi imple-
mentata in 3 clase: BestExamScore, BestPartialScore si BestTotalScore. Fiecare
alege studentul in functie de un criteriu, iar ca profesorul sa afle care e
cel mai bun student al sau, apeleaza metoda din curs getBestStudent.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Care e lungul proces de adaugare al notelor in catalog si de notificare al 
parintilor?

	Povestea incepe din clasa ScoreVisitor care implementeaza interfata 
Visitor, proiectand evident sablonul Visitor. L-am realizat si pe acesta 
Singleton pentru a ne referii doar o data la el. Avem membrii:
	- exam Scores (ArrayList): retine notele nevalidate din examen
	- partial Scores (ArrayList): retine notele nevalidate din partial

	... si avem metodele:
	- visit(Assistant): asistentul valideaza notele, care sunt adaugate
	in catalog si notifica parintii
	- visit(Teacher): profesorul valideaza notele, care sunt adaugate
	in catalog si notifica parintii
	- addPartialGrade: adauga la lista de note nevalidate din partial o 
	noua nota 
	- addExamGrade: adauga la lista de note nevalidate din examen o 
	noua nota 

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Interfata Grafica: StudentPage

	Elementele principale care ies in evidenta cand este afisata pagina de
studenti sunt urmatoarele:
	- titlu: Electronic Catalog <- Jlabel
	- hello personalizat <- Jlabel
	- lista de cursuri  <-Jlist
	- panel unde vor fi afisate toate informatiile <-JPanel
	- buton log off care poate va functiona intr-un viitor apropiat <-JButton

	La alegerea unui curs vor aparea toate informatiile necesare pe panel-ul
de informatii precum:
	- titlul cursului ales <-JLabel
	- Profesorul titular <- Jlabel
	- Asistentul grupei <- Jlabel
	- lista de toti asistentii aferenti cursului <- Jlist
	- Partial Grade, Exam Grade, Total Grade <- Jlabel
	- Mesaj de promovare <- Jlabel

	Nu am folosit layout specific, pentru ca mi s-a parut mai usor de lucrat
cu coordonatele si aranjate stilistic mai frumos, si nici nu se cere layout anume.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Interfata Grafica: TeacherAssistantPage

	Elementele principale care ies in evidenta cand este afisata aceasta 
pagina sunt aceleasi cu pagina de student, insa pe panel-ul vor fi afisate
alte componente:
	- lista cu notele pe care asistentul/profesorul trebuie sa le valideze
	- buton de validare prin care se face apelarea la metodele din 
	ScoreVisitor. In momentul cand au fost validate notele, pe ecran apare
	mesajul ca nu mai are ce note valida.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Interfata Grafica: ParentPage

	Pagina de Parinte e asemanatoare cu pagina studentului, insa pentru a
putea vedea notificarile, se poate observa aparitia unui nou buton "Notifications".
In momentul apasarii, panel-ul de informatii afiseaza lista de notificafari.
N-am stiut daca sa consider ca o data ce a vazut lista, sa dispara notificarile
deja vazute sau nu, asa ca am lasat sa ramana si notificarile vechi.

Mini Parere de rau: Am observat la liste ca daca dau Enabled false, nu pot schimba
culoare elementelor listei si n-am gasit o solutie sa le coloreze:(

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Testarea

	Am realizat parsarea fisierului de tip json si am realizat doua teste
puse in comentariu care sa arate functionalitatea temei: una normala si una
pentru interfata grafica.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~