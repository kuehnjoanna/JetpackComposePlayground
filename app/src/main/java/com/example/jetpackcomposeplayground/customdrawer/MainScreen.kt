package com.example.jetpackcomposeplayground.customdrawer

import android.annotation.SuppressLint

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposeplayground.customdrawer.component.CustomDrawer
import com.example.jetpackcomposeplayground.customdrawer.model.CustomDrawerState
import com.example.jetpackcomposeplayground.customdrawer.model.NavigationItem
import com.example.jetpackcomposeplayground.customdrawer.model.isOpened
import com.example.jetpackcomposeplayground.customdrawer.model.opposite
import kotlin.math.roundToInt

@Composable
fun MainScreen(){
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Home) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )


    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .fillMaxSize()
    ){
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it
            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )
        MainContent(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black,
                    alpha = 0.1f,
                    shadowRadius = 50.dp
                ),
            drawerState = drawerState,
            onDrawerClick = { drawerState = it },
            )
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit
){
    val images = remember {
        mutableListOf(
            "https://cdn.pixabay.com/photo/2024/04/18/19/04/lock-8704819_960_720.jpg",
            "https://cdn.pixabay.com/photo/2024/04/18/19/04/lock-8704819_960_720.jpg",
            "https://cdn.pixabay.com/photo/2024/04/18/19/04/lock-8704819_960_720.jpg",
            "https://cdn.pixabay.com/photo/2024/04/18/19/04/lock-8704819_960_720.jpg")
    }
    val pagerState = rememberPagerState( pageCount = {images.size})

    Scaffold(
        modifier = modifier
            .clickable(enabled = drawerState == CustomDrawerState.Opened){
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )

                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){




            AnimatedBorderCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                shape = RoundedCornerShape(8.dp),
                gradient = Brush.sweepGradient(listOf(Color.Magenta, Color.Cyan)),
                onCardClick = {}
            ) {
                Column(modifier = Modifier.padding(all = 24.dp)) {
//                    Text(
//                        text = "Home",
//                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                        fontWeight = FontWeight.Medium
//                    )

                    HorizontalPager(
                        state = pagerState) {index ->

                        AnimatedBorderCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 24.dp),
                            shape = RoundedCornerShape(8.dp),
                            gradient = Brush.sweepGradient(listOf(Color.Magenta, Color.Cyan)),
                            onCardClick = {}
                        ) {

                            AsyncImage(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp)
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(images[index])
                                    .build(),
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop
                            )

                        }
                    }
                }

            }
        }
    }

}

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 10000,
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit
){
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier.clickable { onCardClick() },
        shape = shape
    ){
       Surface(
           modifier = Modifier
               .fillMaxWidth()
               .padding(borderWidth)
               .drawWithContent {
                   rotate(degrees = degrees) {
                       drawCircle(
                           brush = gradient,
                           radius = size.width,
                           blendMode = BlendMode.SrcIn,
                       )
                   }
                   drawContent()
               },
           color = MaterialTheme.colorScheme.surface,
           shape = shape
       ) {
            content() // try commented out
       }
    }

}

