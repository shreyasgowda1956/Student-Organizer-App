package com.example.studentorganizer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity:ComponentActivity(){override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{Organizer()}}}
sealed class Screen(val route:String){data object Dashboard:Screen("dashboard");data object Assignments:Screen("assignments");data object Profile:Screen("profile");data object Settings:Screen("settings");data object Add:Screen("add_assignment")}
data class Task(val id:Long,val title:String,val course:String,val due:String,val priority:String,val completed:Boolean=false)
private val blue=Color(0xFF4F46E5)
@Composable fun Organizer(){
 var dark by remember{mutableStateOf(false)};var notice by remember{mutableStateOf(true)};var start by remember{mutableStateOf("Dashboard")}
 val nav= rememberNavController()
 val tasks= remember{mutableStateListOf(Task(1,"Research Paper Draft","CS101","Today, 5 PM","High"),Task(2,"Calculus Problem Set","MATH201","Tomorrow","Medium"),Task(3,"UI Design Reflection","DES110","Oct 15","Low",true),Task(4,"Database ER Diagram","CS220","Oct 16","High"),Task(5,"Physics Lab Report","PHY105","Oct 18","Medium"),Task(6,"Reading Quiz","ENG102","Oct 20","Low"),Task(7,"Group Presentation","CS101","Oct 22","High"))}
 MaterialTheme(colorScheme=if(dark)darkColorScheme(primary=blue)else lightColorScheme(primary=blue)){
 val route=nav.currentBackStackEntryAsState().value?.destination?.route
 Scaffold(bottomBar={if(route!=Screen.Add.route)Bottom(nav,route)}){p->NavHost(nav,if(start=="Assignments")Screen.Assignments.route else Screen.Dashboard.route,Modifier.padding(p)){
 composable(Screen.Dashboard.route){Column{Text("Welcome back, Shreyas! 👋",Modifier.padding(20.dp),style=MaterialTheme.typography.headlineSmall);Row{Stat("Active Courses","4");Stat("Pending Tasks",tasks.count{!it.completed}.toString())};Text("Active Courses",Modifier.padding(16.dp));LazyRow{items(listOf("CS101 · Intro to Computing","MATH201 · Calculus II","DES110 · Design Basics","CS220 · Database Systems")){Card(Modifier.padding(6.dp)){Text(it,Modifier.padding(16.dp))}}};Text("Urgent Deadlines",Modifier.padding(16.dp));tasks.filter{!it.completed}.take(2).forEach{TaskRow(it){toggle(tasks,it.id)}};Spacer(Modifier.height(8.dp));Button(onClick={nav.navigate(Screen.Add.route)},Modifier.padding(16.dp)){Icon(Icons.Default.Add,null);Text(" Add Assignment")}}}
 composable(Screen.Assignments.route){var filter by remember{mutableStateOf("All")};Column{Top("My Assignments");Row{listOf("All","Pending","Completed").forEach{FilterChip(filter==it,{filter=it},label={Text(it)})}};LazyColumn{items(tasks.filter{filter=="All"||(filter=="Pending"&&!it.completed)||(filter=="Completed"&&it.completed)}){TaskRow(it){toggle(tasks,it.id)}}};Button(onClick={nav.navigate(Screen.Add.route)},Modifier.padding(12.dp)){Text("+ Add Assignment")}}}
 composable(Screen.Profile.route){Column{Top("Student Profile");Card(Modifier.fillMaxWidth().padding(16.dp)){Column(Modifier.padding(20.dp)){Text("SG",color=blue);Text("Shreyas Gowda",style=MaterialTheme.typography.headlineSmall);Text("PES1UG24CS704");Text("Computer Science Engineering")}};Stat("GPA","3.82");Stat("Completed Credits","84");Text("Current Semester: Fall 2026",Modifier.padding(16.dp));Button(onClick={},Modifier.fillMaxWidth().padding(12.dp)){Text("Edit Profile")};OutlinedButton(onClick={},Modifier.fillMaxWidth().padding(12.dp)){Text("Export Academic Summary")}}}
 composable(Screen.Settings.route){Column{Top("Settings");Row(verticalAlignment=Alignment.CenterVertically){Text("Dark Mode",Modifier.weight(1f));Switch(dark,{dark=it})};Row(verticalAlignment=Alignment.CenterVertically){Text("Push Notifications",Modifier.weight(1f));Checkbox(notice,{notice=it})};Text("Default Start Screen",Modifier.padding(16.dp));listOf("Dashboard","Assignments").forEach{Row(verticalAlignment=Alignment.CenterVertically){RadioButton(start==it,{start=it});Text(it)}}}}
 composable(Screen.Add.route){var title by remember{mutableStateOf("")};var course by remember{mutableStateOf("")};var due by remember{mutableStateOf("")};var priority by remember{mutableStateOf("Medium")};var desc by remember{mutableStateOf("")};var reminder by remember{mutableStateOf(false)};val valid=title.isNotBlank()&&course.isNotBlank()&&due.isNotBlank();Column{Top("Add New Assignment",true){nav.popBackStack()};Column(Modifier.weight(1f).padding(16.dp)){OutlinedTextField(title,{title=it},label={Text("Assignment title *")},modifier=Modifier.fillMaxWidth());OutlinedTextField(course,{course=it},label={Text("Course name / code *")},modifier=Modifier.fillMaxWidth());OutlinedTextField(due,{due=it},label={Text("Due date *")},trailingIcon={IconButton(onClick={if(due.isBlank())due="Oct 20, 2026"}){Icon(Icons.Default.CalendarMonth,null)}},modifier=Modifier.fillMaxWidth());Text("Priority level");Row{listOf("Low","Medium","High").forEach{Row(verticalAlignment=Alignment.CenterVertically){RadioButton(priority==it,{priority=it});Text(it)}}};OutlinedTextField(desc,{desc=it},label={Text("Description / notes")},minLines=3,modifier=Modifier.fillMaxWidth());Row(verticalAlignment=Alignment.CenterVertically){Checkbox(reminder,{reminder=it});Text("Enable Reminder Notification")}};Row{OutlinedButton(onClick={nav.popBackStack()},Modifier.weight(1f)){Text("Cancel")};Button(enabled=valid,onClick={tasks.add(0,Task(System.currentTimeMillis(),title,course,due,priority));nav.popBackStack()},Modifier.weight(1.4f)){Text("Save Assignment")}}}}
 }}
 }
}
private fun toggle(list:MutableList<Task>,id:Long){val i=list.indexOfFirst{it.id==id};if(i>=0)list[i]=list[i].copy(completed=!list[i].completed)}
@Composable fun Top(title:String,back:Boolean=false,onBack:()->Unit={}){Row(verticalAlignment=Alignment.CenterVertically,modifier=Modifier.padding(16.dp)){if(back)IconButton(onClick=onBack){Icon(Icons.Default.ArrowBack,null)};Text(title,style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold)}}
@Composable fun Bottom(nav:NavHostController,route:String?){NavigationBar{listOf(Triple(Screen.Dashboard,Icons.Default.Home,"Home"),Triple(Screen.Assignments,Icons.Default.CheckCircle,"Tasks"),Triple(Screen.Profile,Icons.Default.Person,"Profile"),Triple(Screen.Settings,Icons.Default.Settings,"Settings")).forEach{(s,i,l)->NavigationBarItem(route==s.route,{nav.navigate(s.route){popUpTo(Screen.Dashboard.route){saveState=true};launchSingleTop=true;restoreState=true}},{Icon(i,null)},label={Text(l)})}}}
@Composable fun Stat(title:String,value:String){Card(Modifier.padding(8.dp),shape=RoundedCornerShape(16.dp)){Column(Modifier.padding(14.dp)){Text(value,style=MaterialTheme.typography.headlineMedium,color=blue);Text(title)}}}
@Composable fun TaskRow(t:Task,click:()->Unit){Card(Modifier.fillMaxWidth().padding(horizontal=12.dp,vertical=5.dp)){Row(verticalAlignment=Alignment.CenterVertically,modifier=Modifier.padding(8.dp)){Checkbox(t.completed,{click()});Column(Modifier.weight(1f)){Text(t.title,fontWeight=FontWeight.SemiBold);Text(t.course);Text(t.due)};Text(t.priority,color=blue)}}}
