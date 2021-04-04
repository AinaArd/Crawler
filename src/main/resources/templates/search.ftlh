<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="author" content="colorlib.com">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet"/>
    <link href="/css/main.css" rel="stylesheet"/>
</head>

<body>
<div class="s01">
    <form method="post">
        <div class="inner-form">
            <div class="input-field first-wrap">
                <input id="search" name="search" type="text" placeholder="What are you looking for?"/>
            </div>
            <div class="input-field second-wrap">

            </div>
            <div class="input-field third-wrap">
                <input class="btn-search" type="submit" value="Search"/>
            </div>
        </div>

        <#if result??>
            <div style="color:white">
                ${result}
            </div>
        </#if>

    </form>
</div>
</body>
</html>
