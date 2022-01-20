# ComposeGraphLibrary 📈

## Table of Contents 📝 
+ [About](#about)
+ [Getting Started](#getting_started)
+ [Usage](#usage)

## About <a name = "about"></a>
The purpose of this project is to visualize data using different charts. The charts should be accessible as composables. 
## How does it look 📊:
<p align="center">
  <a href="" rel="noopener">
 <img width=760px height=500pximg src="https://i.imgur.com/dTnl35Z.png" alt="Example charts"></a>
</p>

## Getting Started <a name = "getting_started"></a>

### Gradle

Add the following repository and gradle to your depdendency.

project.gradle

```
repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/asgerakerman/composegraphlibrary")
            credentials {
                username = username
                password = token
            }

        }
    }
```
Here, you should enter the given token, and you github username. 
module.gradle

```
implementation 'com.example.composegraphlibrary:release:1.0.3'
```

## Usage 🛠 <a name = "usage"></a>

To use the composables, simply format your data to match the chartdata of your needs, and initialize your composable as followed:

```
LineChartComposable(
            data = lineGraphData,
            description = description,
            unit = units,
            styleConfig = styleConfig
        )
```

### Data
As an example, the single datapoints for the linechart should follow the format as below:
```
data class LineChartDataPoint(
    val yValue: Float,
    val xLabel: Float
)
```

### StyleConfig
The theming and design of the charts can be changed by changing the different variables of the belonging StyleConfig file that needs to be initialize and passed for the composable:

```
data class LineChartStyleConfig(
    var quadrantYLineColor: Color = Color.Gray,
    var quadrantDottedLineColor: Color = Color.LightGray,
    var quadrantPathLineColor: Color = Color.Blue,
    var quadrantPointColor: Color = Color.Blue,
    var quadrantPointWidth: Float = 4f,
    var quadrantLineWidth: Float = 3f,

    var xAxisLineColor: Color = Color.Gray,
    var xAxisLineWidth: Float = 4f,

    var yAxisLineColor: Color = Color.Gray,
    var yAxisLabelSize: TextUnit = 44.sp,
    var yAxisLineWidth: Float = 4f
    )
```
