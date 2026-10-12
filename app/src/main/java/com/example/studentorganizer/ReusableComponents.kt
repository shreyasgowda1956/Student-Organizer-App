package com.example.studentorganizer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CourseInfo(courseName:String,courseCode:String,instructor:String,progress:Float) {
    Card(Modifier.width(230.dp).padding(6.dp),shape=RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(16.dp)) {
            Icon(Icons.Default.School,null,tint=Color(0xFF4F46E5))
            Text(courseCode,color=Color(0xFF4F46E5),fontWeight=FontWeight.Bold)
            Text(courseName,fontWeight=FontWeight.SemiBold)
            Text("Instructor · $instructor",fontSize=12.sp)
            LinearProgressIndicator(progress={progress},modifier=Modifier.fillMaxWidth().padding(top=8.dp))
        }
    }
}

@Composable
fun AssignmentForm(
    title:String,course:String,dueDate:String,priority:String,description:String,
    onTitleChange:(String)->Unit,onCourseChange:(String)->Unit,onDueDateChange:(String)->Unit,
    onPriorityChange:(String)->Unit,onDescriptionChange:(String)->Unit
) {
    OutlinedTextField(title,onTitleChange,label={Text("Assignment title *")},singleLine=true,modifier=Modifier.fillMaxWidth())
    OutlinedTextField(course,onCourseChange,label={Text("Course name / code *")},singleLine=true,modifier=Modifier.fillMaxWidth())
    OutlinedTextField(dueDate,onDueDateChange,label={Text("Due date *")},singleLine=true,modifier=Modifier.fillMaxWidth(),
        trailingIcon={IconButton(onClick={if(dueDate.isBlank())onDueDateChange("Oct 20, 2026")}){Icon(Icons.Default.CalendarMonth,"Fill sample date")}})
    Text("Priority level",fontWeight=FontWeight.SemiBold)
    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) {
        listOf("Low","Medium","High").forEach { option ->
            Row(verticalAlignment=Alignment.CenterVertically) {
                RadioButton(priority==option,{onPriorityChange(option)})
                Text(option,fontSize=13.sp)
            }
        }
    }
    OutlinedTextField(description,onDescriptionChange,label={Text("Description / notes")},minLines=3,maxLines=5,modifier=Modifier.fillMaxWidth())
}


/** Data used by the dashboard course cards. */
data class Course(
    val name: String,
    val code: String,
    val instructor: String,
    val progress: Float
)

@Composable
fun StudentProfile(name: String, studentId: String, program: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Student",
                tint = Color(0xFF4F46E5),
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(studentId, color = Color(0xFF4F46E5), fontWeight = FontWeight.SemiBold)
            Text(program, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
