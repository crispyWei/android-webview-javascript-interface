### Android WebView Javascript interface:
javascript call native sample code, native call javascript and get return value

### Javascript Sample Code:
index.html

```
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <script>
        function showAndroidMethod() {
            //用以下方法call android method
            Android.showAndroidToast("Hello Android!");
        }

        function openLink(){
            window.open("https://maps.google.com/?q=58250 Montaron, France&t=m", "_blank");
        }

        //收到原生訊息後進行的function
        function ChangeStatus(text, color){
            document.getElementById("statusText").textContent = text;
            document.getElementById("statusText").style.color = color;
            return true;
        }
    </script>
</head>

<body>
    <input type="button" value="呼叫Android原生Interface" onClick="showAndroidMethod()" />
    <div>
        <span id="status">来自Android的消息:</span><span id="statusText">還沒有任何狀態</span>
    </div>
    <a href="https://maps.google.com/?q=58250 Montaron, France&t=m">May open Google map</a>
    <br/>
    <button onclick="openLink()">May open Map</button>
    <br/>
    <a href="googlechrome://https://google.com">try it on Chrome</a>
</body>

</html>
```

