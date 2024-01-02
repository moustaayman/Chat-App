<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"
            integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w=="
            crossorigin="anonymous"
    />
    <link rel="stylesheet" href="styles_loginSignup.css" />
    <title>Chat App</title>
</head>

<body>
<div class="container">
    <div class="forms-container">
        <div class="signin-signup">
            <form action="UserLoginServlet" method="POST" class="sign-in-form">
                <h2 class="title">Sign In</h2>
                <div class="input-field">
                    <i class="fas fa-user"></i>
                    <input type="email" id="email" name="email" placeholder="Email" required />
                </div>
                <div class="input-field">
                    <i class="fas fa-lock"></i>
                    <input type="password" id="password" name="password" placeholder="Password" required />
                </div>
                <input type="submit" value="Login" class="btn solid" />
            </form>
            <form action="UserCreationServlet" method="POST" class="sign-up-form">
                <h2 class="title">Sign Up</h2>
                <div class="input-field">
                    <i class="fas fa-user"></i>
                    <input
                            type="text"
                            placeholder="Username"
                            name="username"
                            id="username"
                            required
                    />
                </div>
                <div class="input-field">
                    <i class="fas fa-user"></i>
                    <input
                            type="text"
                            placeholder="Firstname"
                            name="firstname"
                            id="firstname"
                            required
                    />
                </div>
                <div class="input-field">
                    <i class="fas fa-user"></i>
                    <input
                            type="text"
                            placeholder="Lastname"
                            name="lastname"
                            id="lastname"
                            required
                    />
                </div>
                <div class="input-field">
                    <i class="fas fa-envelope"></i>
                    <input
                            type="email"
                            placeholder="Email"
                            name="new_email"
                            id="new_email"
                            required
                    />
                </div>
                <div class="input-field">
                    <i class="fas fa-lock"></i>
                    <input
                            type="password"
                            placeholder="Password"
                            name="new_password"
                            id="new_password"
                            required
                    />
                </div>
                <input type="submit" value="sign up" class="btn solid" />
            </form>
        </div>
    </div>

    <div class="panels-container">
        <div class="panel left-panel">
            <div class="content">
                <h3>New here?</h3>
                <p>What are you waiting for? Join Us!</p>
                <button class="btn transparent" id="sign-up-btn">Sign up</button>
            </div>
            <img src="images/1.png" class="image" alt="" />
        </div>

        <div class="panel right-panel">
            <div class="content">
                <h3>Already a member?</h3>
                <p>Hurry Up! There are messages waiting for you.</p>
                <button class="btn transparent" id="sign-in-btn">Sign in</button>
            </div>
            <img src="images/2.png" class="image" alt="" />
        </div>
    </div>
</div>

<script src="loginSignup.js"></script>
</body>
</html>
