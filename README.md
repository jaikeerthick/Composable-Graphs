<img src="/Composable-Graphs-Poster.svg">

# Composable-Graphs
[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![CodeQL](https://github.com/jaikeerthick/Composable-Graphs/actions/workflows/codeql.yml/badge.svg)](https://github.com/jaikeerthick/Composable-Graphs/actions/workflows/codeql.yml)
[![](https://jitpack.io/v/jaikeerthick/Composable-Graphs.svg)](https://jitpack.io/#jaikeerthick/Composable-Graphs)
![tag](https://img.shields.io/github/license/jaikeerthick/Composable-Graphs)
[![Jetpack Compose](https://img.shields.io/badge/Built%20with-Jetpack%20Compose%20%E2%9D%A4%EF%B8%8F-2DA042)](https://developer.android.com/jetpack/compose)<br>
<a href="https://jetc.dev/issues/128.html"><img alt="jetc.dev" src="https://img.shields.io/badge/jetc.dev-%23128-343a40.svg?style=flat&logo=jetpackcompose"/></a>

✨ A very Minimal, Sleek and Lightweight Graph library for Android using <b>Jetpack Compose</b>

## Important ⚠️
Please migrate to ```v1.2.2``` or above if you are using lower versions and refer the updated documentation below for the usage

## Gradle Setup
Latest version: [![](https://jitpack.io/v/jaikeerthick/Composable-Graphs.svg)](https://jitpack.io/#jaikeerthick/Composable-Graphs)

In ```settings.gradle.kts```, Add jitpack url
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven(url = "https://jitpack.io") // Add jitpack
    }
}
```
In ```build.gradle.kts```, in ```dependencies``` block, add
```kotlin
implementation("com.github.jaikeerthick:Composable-Graphs:v{version}") //ex: v1.2.3
```
## Graphs Available
1. Line Graph
2. Bar Graph
3. Pie Chart
4. Donut Chart (Normal, Progressive)

## Preview

<p>
<img width="250px" src="/screenshot/Screenshot-1.png"/>
&nbsp;&nbsp;&nbsp;<img width="250px" src="/screenshot/Screenshot-2.png" />
&nbsp;&nbsp;&nbsp;<img width="250px" src="/screenshot/Screenshot-3.png"/>
<br/><br/>
<img width="250px" src="/screenshot/Screenshot-4.png" />
<p/>
<br/>


# Usage

### 1. Line Graph

```kotlin
val data = listOf(LineData(x = "Sun", y = 200), LineData(x = "Mon", y = 40))

LineGraph(
    modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 12.dp),
    data = data,
    onPointClick = { value: LineData ->
        // do something with value
    },
)
```

### 2. Bar Graph

```kotlin
BarGraph(
    data = listOf(BarData(x = "22", y = 20), BarData(x = "23", y = 30)),
)
```

### 3. Pie Chart
```kotlin
// sample data
val pieChartData = listOf(
    PieData(value = 130F, label = "HTC", color = Color.Green),
    PieData(value = 260F, label = "Apple", labelColor = Color.Blue),
    PieData(value = 500F, label = "Google"),
)

// composable
PieChart(
    modifier = Modifier
        .padding(vertical = 20.dp)
        .size(220.dp),
    data = pieChartData,
    onSliceClick = { pieData ->
        Toast.makeText(context, "${pieData.label}", Toast.LENGTH_SHORT).show()
    }
)
```
### 4. Donut Chart
Donut Chart supports 2 types of implementations: ```DonutChartType.Normal``` and ```DonutChartType.Progressive()```
```kotlin
// sample values
val donutChartData = listOf(
    DonutData(value = 30F),
    DonutData(value = 60F),
    DonutData(value = 70F),
    DonutData(value = 50F),
)

// composable
DonutChart(
    modifier = Modifier
        .padding(vertical = 20.dp)
        .size(220.dp),
    data = donutChartData,
    type = DonutChartType.Progressive(),
    style = viewModel.donutChartStyle,
)
```

## Styling

Create stunning & colorful graphs with awesome styling🎨. Composable-Graphs supports various styling helpers:

- Modifier (Yes, We heard you!💬)
- Visibility
- Colors
- LabelPosition

``` kotlin
val style = BarGraphStyle(
                    visibility = BarGraphVisibility(
                        isYAxisLabelVisible = true
                    ),
                    yAxisLabelPosition = LabelPosition.RIGHT
                )

val style2 = LineGraphStyle(
                    visibility = LinearGraphVisibility(
                        isYAxisLabelVisible = false,
                        isCrossHairVisible = true
                    ),
                    colors = LinearGraphColors(
                        lineColor = GraphAccent2,
                        pointColor = GraphAccent2,
                        clickHighlightColor = PointHighlight2,
                        fillType = LineGraphFillType.Gradient(
                            brush = Brush.verticalGradient(listOf(Color.Green, Color.Yellow))
                        )
                    )
                )
````


## Work In Progress (v1.2.4) :

### Features
- Show y values above points & bars (sticky)

### Interaction
- Ability to turn off interaction on charts (disable clicks)

### Grid
- Customize grid color
- Ability to control visibility of x and y axis grids separately

### Highlight
- Highlight Shape for Line Graph - Circle, HallowedCircle, Triangle, Square
- Control size of the highlight

<br/>
<br/>

## Contributions are welcome ❤️

