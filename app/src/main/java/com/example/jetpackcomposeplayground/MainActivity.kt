package com.example.jetpackcomposeplayground


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposePlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyUI()
                }
            }
        }
    }
}
@Preview
@Composable
fun MyUI() {
    val scaffoldState = rememberScaffoldState() // This should be fine for preview

    Scaffold(
        bottomBar = { MyBottomBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Preview Safe */ },
                contentColor = Color.Red,
                modifier = Modifier
                    .offset(y = 50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24), // Ensure this resource exists
                    contentDescription = "add",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            dashboard()
        }
    }
}
data class BottomMenuItem(val label: String, val icon: Painter)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemlist = arrayListOf<BottomMenuItem>()

    bottomMenuItemlist.add(
        BottomMenuItem(
            label = " ",
            icon = painterResource(id = R.drawable.search)
        )
    )
    bottomMenuItemlist.add(
        BottomMenuItem(
            label = " ",
            icon = painterResource(id = R.drawable.baseline_home_24)
        )
    )
    bottomMenuItemlist.add(
        BottomMenuItem(
            label = " ",
            icon = painterResource(id = R.drawable.baseline_camera_alt_24)
        )
    )
    bottomMenuItemlist.add(
        BottomMenuItem(
            label = " ",
            icon = painterResource(id = R.drawable.baseline_settings_24)
        )
    )

    return bottomMenuItemlist
}
@Composable
fun MyBottomBar() {
    val bottomMenuItemsList = prepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Profile")
    }

    BottomAppBar() {
        bottomMenuItemsList.forEachIndexed { index, bottomMenuItem ->
            if (index == 2) {
                BottomNavigationItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                       },
                    enabled = false
                )
            }

            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    Toast.makeText(contextForToast, bottomMenuItem.label, Toast.LENGTH_SHORT)
                        .show()
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                    )
                },
                label = {
                    Text(text = bottomMenuItem.label, modifier = Modifier.padding(top = 14.dp))
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Preview(showBackground = true)
@Composable
fun dashboard() {
    JetpackComposePlaygroundTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(android.graphics.Color.parseColor("#D7C3F1")))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backrounds),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(90.dp)
                        .clip(shape = CircleShape)
                        .clickable { }
                )
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 14.dp)
                        .weight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "John Wick",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "John Wick",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 14.dp)
                    )


                }
            }
            var text by rememberSaveable {
                mutableStateOf(" ")
            }
            TextField(
                value = text, onValueChange = {
                    text = it
                },
                label = { Text(text = "Searching for... ") },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .height(45.dp)
                            .width(45.dp)
                            .background(
                                Color(android.graphics.Color.parseColor("#D7C3F1")),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 6.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Magenta,
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#0D7C66"))

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .background(Color.White, CircleShape)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)

            )
            {
                Column(
                    Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp)
                        .shadow(
                            elevation = 25.dp,
                            ambientColor = Color.Blue,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color(android.graphics.Color.parseColor("#9599be")),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Box(
                        modifier = Modifier
                            .height(65.dp)
                            .width(75.dp)
                            .shadow(
                                elevation = 25.dp,
                                ambientColor = Color.Blue,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = Color(android.graphics.Color.parseColor("#D7C3F1")),
                                shape = RoundedCornerShape(20.dp)
                            )
                          ,
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(40.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor("#7e57c2")),
                                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Messenger",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }


                }
                Column(
                    Modifier
                        .weight(0.5f)
                        .padding(start = 12.dp)
                        .shadow(
                            elevation = 25.dp,
                            ambientColor = Color.Blue,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color(android.graphics.Color.parseColor("#9599be")),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Box(
                        modifier = Modifier
                            .height(65.dp)
                            .width(75.dp)
                            .shadow(
                                elevation = 25.dp,
                                ambientColor = Color.Blue,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = Color(android.graphics.Color.parseColor("#D7C3F1")),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.map),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(40.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor("#7e57c2")),
                                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Maps",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)

            )
            {
                Column(
                    Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp)
                        .shadow(
                            elevation = 25.dp,
                            ambientColor = Color.Blue,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color(android.graphics.Color.parseColor("#9599be")),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Box(
                        modifier = Modifier
                            .height(65.dp)
                            .width(75.dp)
                            .shadow(
                                elevation = 25.dp,
                                ambientColor = Color.Blue,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = Color(android.graphics.Color.parseColor("#D7C3F1")),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(40.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor("#7e57c2")),
                                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Charts",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }


                }
                Column(
                    Modifier
                        .weight(0.5f)
                        .padding(start = 12.dp)
                        .shadow(
                            elevation = 25.dp,
                            ambientColor = Color.Blue,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color(android.graphics.Color.parseColor("#9599be")),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Box(
                        modifier = Modifier
                            .height(65.dp)
                            .width(75.dp)
                            .shadow(
                                elevation = 25.dp,
                                ambientColor = Color.Blue,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = Color(android.graphics.Color.parseColor("#D7C3F1")),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_move_to_inbox_24),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(40.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor("#7e57c2")),
                                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Inbox",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }


            }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .height(120.dp)
                    .shadow(
                        elevation = 25.dp,
                        ambientColor = Color.Blue,
                        spotColor = Color.Black,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.parseColor("#000000")),
                                Color(android.graphics.Color.parseColor("#000000")),
                                Color(android.graphics.Color.parseColor("#7e57c2"))
                            )
                        ), shape = RoundedCornerShape(25.dp)
                    )
            ) {
                val (img, text1, text2) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.blackbg),
                    contentDescription = " "
                )
                Text(
                    text = "Upgrade to PRO",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center)
                        .padding(start = 20.dp)
                )
            }

        }
    }
}