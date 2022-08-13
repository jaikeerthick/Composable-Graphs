# Composable-Graphs ( Jetpack Compose ✔)
![tag](https://img.shields.io/github/license/jaikeerthick/Composable-Graphs)

✨ A very Minimal, Sleek and Powerful Graph library for Android using <b>Jetpack Compose<b/>
    
## Gradle Setup

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.jaikeerthick:Composable-Graphs:v1.0'
}
```

## Screenshots

<p>
<img width="400px" src="https://raw.githubusercontent.com/jaikeerthick/Composable-Graphs/master/screenshot_1.png" />
<img width="375px" src="https://raw.githubusercontent.com/jaikeerthick/Composable-Graphs/master/screenshot_2.png" />
<p/>
<br/>


## Usage
    
1. Bar Graph

```kotlin
BarGraph(
  dataList = listOf(20, 30, 10, 60, 35), //  dataList : List<Number>
)
```
    
2. Line Graph

```kotlin
LineGraph(
    xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
        GraphData.String(it)
    }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
    yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
)
```
    
Example for passing String and Number:
    
```kotlin
xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
                       GraphData.String(it)
            }

xAxisData = listOf(20, 40, 30, 50, 70, 0, 40).map {
                       GraphData.Number(it)
            }
``` 
    
<b>Note:<b/> ```xAxisData``` can be of ```GraphData.String``` & ```GraphData.Number```, but ```yAxisData``` can be only of type ```Number```

## Styling
    
```BarGraphStyle``` and ```LineGraphStyle``` can be used to style the Graphs effetively

You can control the following things using Style classes:

- Height
- Padding
- Colors
- Visibility - control visibility for crosshair, labels, headers, etc.
    
``` kotlin
// BarGraph
val style = BarGraphStyle(
                    visibility = BarGraphVisibility(
                        isYAxisLabelVisible = true
                    )
                )

// LineGraph
val style2 = LineGraphStyle(
                    visibility = LinearGraphVisibility(
                        isHeaderVisible = true,
                        isYAxisLabelVisible = false,
                        isCrossHairVisible = true
                    ),
                    colors = LinearGraphColors(
                        lineColor = GraphAccent2,
                        pointColor = GraphAccent2,
                        clickHighlightColor = PointHighlight2,
                        fillGradient = Brush.verticalGradient(
                            listOf(Gradient3, Gradient2)
                        )
                    )
                )
````
And you can pass it to the graph like this:
````kotlin
 BarGraph(
    dataList = listOf(20, 30, 10, 60, 35),
    style = style
)   
````
