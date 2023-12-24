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

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.jaikeerthick:Composable-Graphs:v1.2.2'
}
```
## Graphs Available
1. Line Graph
2. Bar Graph

## Preview

<p>
<img width="250px" src="/Screenshot-1.png"/>
&nbsp;&nbsp;&nbsp;<img width="250px" src="/Screenshot-2.png" />
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


<br/>
<br/>

![](https://forthebadge.com/images/badges/built-with-love.svg)

## Contribution:
Fork the repo and create PRs 🦄

