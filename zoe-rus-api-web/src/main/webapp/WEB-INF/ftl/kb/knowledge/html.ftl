<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
<#list data.css as css>
    <link type="text/css" rel="stylesheet" href="/css/${css}.css"/>
</#list>
</head>
<body>
${data.html!""}
</body>
</html>
