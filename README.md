# Composable-Graphs ( Jetpack Compose )
![tag](https://img.shields.io/github/license/jaikeerthick/Composable-Graphs)

✨ A very Minimal, Sleek and Lightweight Graph library for Android using <b>Jetpack Compose<b/>

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
    data = listOf(BarData(x = "22", y = 20), BarData(x = "23", y = 30)),
)
```

2. Line Graph

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

## Styling

Create stunning & colorful graphs with awesome styling 🎨

Composable-Graphs supports various styling helpers:

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
                        isHeaderVisible = true,
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

