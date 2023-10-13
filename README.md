# AntColony
```
In this program, we will implement the Ant Colony Optimization (ACO) algorithm to solve the Traveling Salesman Problem (TSP).

The TSP is a well-known problem in computer science. Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city exactly once and returns to the origin city?

That is exactly what we will be solving in this program. We will be using the ACO algorithm to find the shortest route for a list of cities (nodes) in a perfect graph (complete graph) with weighted edges (distances between cities).

The ACO implemented in this program is going to use a probability formula to determine the next city to visit. The probability formula is based on the pheromone level and the distance between cities. The pheromone level is the amount of pheromone left by the previous ants that visited the city. The distance between cities is the distance between the current city and the next city.

Every time an ant visits a city, it will leave a pheromone trail behind. The pheromone trail will evaporate over time. The pheromone trail will also evaporate faster if the pheromone level is high. The pheromone trail will evaporate slower if the pheromone level is low.
```
## How to run

### 1. Compile the program
```
javac Main.java
```

### 2. Run the program

For running this program, you need to specify the tsp file that must be on the TSP folder, running de program using -help will show you the options for the arguments.

This is an example of how to run -help:
```
java Main -help
```
You will see something like this:
```
The arguments are:
-ant <integer> will be the number of ants
-times <integer> will be the number of times we will run the ACO
-file <name.tsp> will be the name of the file in the folder TSP
-alpha <double> will be the alpha value used in the formula
-beta <double> will be the beta value used in the formula
-evaporation <double> will be the evaporation rate used for evaporation
-Q <double> will be the learning rate used in the formula
default values are: -ant5 -times 10 -file TSP/ -alpha 1.0 -beta 1.0 -evaporation 0.01 -Q 1.0
-help will show the help
```
## Experimentation
For this part we will be using the file a280.tsp that is in the TSP folder.
### 1. Default values
```
java Main -file a280.tsp
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 1.582 seconds
ACO finished!
The best ant is:
Ant: 3
Loop: 1
Q: 1.0
Distance traveled: 2786
Path: 1->2->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2786, and the time it took to run the program was 1.582 seconds.

### 2. Changing the number of ants

```
java Main -file a280.tsp -ant 10
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 2.921 seconds
ACO finished!
The best ant is:
Ant: 10
Loop: 6
Q: 1.0
Distance traveled: 2826
Path: 242->1->2->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2826, and the time it took to run the program was 2.921 seconds.

### 3. Changing the number of times we will run the ACO

```
java Main -file a280.tsp -times 20
```
The output will be something like this:
```
Creating graph...
Running ACO...
Progress 60.0%: 12/20
Time: 2.98 seconds
ACO finished!
The best ant is:
Ant: 6
Loop: 2
Q: 1.0
Distance traveled: 2783
Path: 2->1->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2783, and the time it took to run the program was 2.98 seconds.

### 4. Changing the alpha value used in the formula

```
java Main -file a280.tsp -alpha 0.5
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 1.54 seconds
ACO finished!
The best ant is:
Ant: 6
Loop: 3
Q: 1.0
Distance traveled: 2786
Path: 280->1->2->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279
```
As we can see the optimal path is 2786, and the time it took to run the program was 1.54 seconds.

### 5. Changing the beta value used in the formula

```
java Main -file a280.tsp -beta 0.5
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 1.564 seconds
ACO finished!
The best ant is:
Ant: 3
Loop: 0
Q: 1.0
Distance traveled: 2783
Path: 2->1->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2783, and the time it took to run the program was 1.564 seconds.

### 6. Changing the evaporation rate used for evaporation

```
java Main -file a280.tsp -evaporation 0.5
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 1.577 seconds
ACO finished!
The best ant is:
Ant: 2
Loop: 1
Q: 1.0
Distance traveled: 2850
Path: 244->1->2->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2850, and the time it took to run the program was 1.577 seconds.

### 7. Changing the learning rate used in the formula

```
java Main -file a280.tsp -Q 0.5
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 1.587 seconds
ACO finished!
The best ant is:
Ant: 2
Loop: 1
Q: 0.5
Distance traveled: 2783
Path: 2->1->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2783, and the time it took to run the program was 1.587 seconds.

### 8. Changing the number of ants, the number of times we will run the ACO, the alpha value used in the formula, the beta value used in the formula, the evaporation rate used for evaporation and the learning rate used in the formula

```
java Main -file a280.tsp -ant 10 -times 20 -alpha 0.5 -beta 0.5 -evaporation 0.5 -Q 0.5
```
The output will be something like this:
```
Creating graph...
Running ACO...
Time: 5.224 seconds
ACO finished!
The best ant is:
Ant: 11
Loop: 3
Q: 0.5
Distance traveled: 2786
Path: 1->2->3->4->5->6->7->8->9->10->11->12->13->14->15->16->17->18->19->20->21->22->23->24->25->26->27->28->29->30->31->32->33->34->35->36->37->38->39->40->41->42->43->44->45->46->47->48->49->50->51->52->53->54->55->56->57->58->59->60->61->62->63->64->65->66->67->68->69->70->71->72->73->74->75->76->77->78->79->80->81->82->83->84->85->86->87->88->89->90->91->92->93->94->95->96->97->98->99->100->101->102->103->104->105->106->107->108->109->110->111->112->113->114->115->116->117->118->119->120->121->122->123->124->125->126->127->128->129->130->131->132->133->134->135->136->137->138->139->140->141->142->143->144->145->146->147->148->149->150->151->152->153->154->155->156->157->158->159->160->161->162->163->164->165->166->167->168->169->170->171->172->173->174->175->176->177->178->179->180->181->182->183->184->185->186->187->188->189->190->191->192->193->194->195->196->197->198->199->200->201->202->203->204->205->206->207->208->209->210->211->212->213->214->215->216->217->218->219->220->221->222->223->224->225->226->227->228->229->230->231->232->233->234->235->236->237->238->239->240->241->242->243->244->245->246->247->248->249->250->251->252->253->254->255->256->257->258->259->260->261->262->263->264->265->266->267->268->269->270->271->272->273->274->275->276->277->278->279->280
```
As we can see the optimal path is 2786, and the time it took to run the program was 5.224 seconds.

## Conclusion
As we can see, the optimal path for this algorithm is 2783, and the time it took to run the program was 1.54 seconds. This is the best result we got, and it was using the default values, because I already tried changing values and I get to the colclusion that the default values are the best for this algorithm.

In addition, As we can see the optimal ant is always between 1 and 10, which means that is one of the first ants generated, in order of how the algorithm works, when we found an ant with a better path, we replace it, however, we found the best ant in the first 10 ants generated, nevertheless, I'm not saying that we will not found an ant with this path in the next generations, certanly we do, even though, the algorithm is not finding another ant with a path shorter than 2783.

Moreover, talking about the time we found a similar result, the time it took to run the program was 1.54 seconds, and the time it took to run the program with the default values was 1.582 seconds, so the time is not a big difference but, the path with the default values is 2783, and the path with the changed values is 2786, so, the default values are the best for this algorithm.

Finally, I think that the ACO algorithm is a good algorithm for solving the TSP, because it is easy to implement, and it is not a greedy algorithm, so, it will not always give us the same result, and it will not always give us the optimal result, but, it will give us a good result, and it will give us different results, so, we can choose the best result for us.

## Author 
* **Diego Fernández** - [dfernandez19@alumnos.utalca.cl](dfernandez19@alumnos.utalca.cl) - [prodiegus](https://github.com/Prodiegus)
