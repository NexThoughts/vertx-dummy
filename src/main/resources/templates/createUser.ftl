<html>
<head><title>${context.title}</title></head>
<body>
<a href="${context.request().path()+"users"}">${context.name}</a>

<form action="${context.request().path()}users" method="post">
    <div>
        <label>ID</label>
        <input type="text" id="userId" name="userId"/>
    </div>
    <div>
        <label>First Name</label>
        <input type="text" id="firstName" name="firstName"/>
    </div>
    <div>
        <label>Last Name</label>
        <input type="text" id="lastName" name="lastName"/>
    </div>
    <div>
        <label>Age</label>
        <input type="text" id="age" name="age"/>
    </div>
    <div>
        <label>Email</label>
        <input type="text" id="email" name="email"/>
    </div>
    <div class="button">
        <button type="submit">Submit</button>
    </div>
</form>


</body>
</html>