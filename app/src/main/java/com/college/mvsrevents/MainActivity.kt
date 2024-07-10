import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.HeaderViewListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.college.mvsrevents.AllEvents_fragment
import com.college.mvsrevents.AttendedEvents_fragment
import com.college.mvsrevents.R
import com.college.mvsrevents.RegisteredEvents_Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
lateinit var toolbar:Toolbar
lateinit var navigationView: NavigationView
lateinit var drawerLayout: DrawerLayout
lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
lateinit var textView_rollno: TextView
lateinit var textView_username:TextView
//lateinit var headerLayout:LinearLayout
val database= FirebaseDatabase.getInstance()
val reference= database.getReference("students")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        toolbar=findViewById(R.id.toolbar)
        navigationView=findViewById(R.id.navigation_layout)
        drawerLayout=findViewById(R.id.drawer_layout)
        setUpToolbar()
        actionBarDrawerToggle= ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        //menu item selection
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuitem_allevents -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        AllEvents_fragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    toolbar.title="All Events"
                }
                R.id.menuitem_registered_events -> {

                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        RegisteredEvents_Fragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    toolbar.title="Registered Events"
                }
//                R.id.menuitem_attended_events ->{
//
//                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
//                        AttendedEvents_fragment()
//                    ).commit()
//                    drawerLayout.closeDrawers()
//                    toolbar.title="Attended Events"
//                }
//                R.id.certificates -> Toast.makeText(this@MainActivity,
//                    "Certificates", Toast.LENGTH_LONG).show()
            }
            return@setNavigationItemSelectedListener true
        }
    }
    //toolbar hamburger listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
            if(intent!=null){
                val studentRollno:String?=intent.getStringExtra("rollno")
                textView_rollno= findViewById(R.id.textview_rollno)
                if(studentRollno!=null){
                    textView_rollno.setText("Roll No - $studentRollno")
                    // textView_username.setText("Name - $studentName")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //toolbar to actionbar
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title= "title"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
