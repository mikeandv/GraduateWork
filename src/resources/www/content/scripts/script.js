function logIn() {

                    // var constraints = {
                    //     from: {
                    //         email: true
                    //     }
                    // };
                    // alert(validate({from: document.getElementById("email").value}, constraints));

            let xhttp = new XMLHttpRequest();
            let formdata = {
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
            }

            let formJData = JSON.stringify(formdata);

            xhttp.onreadystatechange = function() {

                if (this.readyState == 4 && this.status == 200) {

                    // if(this.responseText.)

                    let result = JSON.parse(this.responseText);

                    if(result == "enter-site") {
                        location.replace("/index2.html");
                    } else if(result == "no-such-user") {
                        document.getElementById("res").innerHTML = "No such user";
                    } else {
                        document.getElementById("res").innerHTML = "Error";
                    }
                    // location.replace("https://www.w3schools.com")

                    // document.getElementById("res").innerHTML = curr_host + "/index.html";
                    // this.responseText;

                };

            }

            xhttp.open("POST", "/login", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.send(formJData);
        }
        function registerForm() {
            // let regForm =

            // "<label name=\"name\">Name:</label>" +
            // "<input type=\"text\" id=\"name_reg\" required><br>" +

            // "<label name=\"email\">E-mail:</label>" +
            // "<input type=\"email\" id=\"email_reg\" required><br>" +

            // "<label name=\"password\">Password:</label>" +
            // "<input type=\"password\" id=\"password_reg\" required><br>" +

            // "<button class=\"button\" type=\"button\" id=\"button_reg\" onclick=\"register()\">Register</button>" +
            // "<div name=\"res\" id=\"res_reg\"></div>";

            // document.getElementById("loginform").innerHTML = regForm;
            location.assign("/users");

        }
        function register() {

            let regFormData = {
                name: document.getElementById("name_reg").value,
                email: document.getElementById("email_reg").value,
                password: document.getElementById("password_reg").value,
            }
            let regFromJData = JSON.stringify(regFormData);
            let xhttp = new XMLHttpRequest;
            xhttp.onreadystatechange = function() {

                if (this.readyState == 4 && this.status == 200) {
                    let result = JSON.parse(this.responseText);
                    if(result == "success") {

                        location.assign("/index.html");
                        sleep(4);
                        document.getElementById("res_reg").innerHTML = "<div id=\"res_reg\">Regirect to login page</div>";

                    } else if (result == "user-already-exists") {
                        document.getElementById("res_reg").innerHTML = "<div id=\"res_reg\">User with such email already exists! Try to log in.</div>";
                    } else {
                        document.getElementById("res_reg").innerHTML = "<div id=\"res_reg\">Unexpected Error.</div>";
                    }

                    // location.replace("https://www.w3schools.com")

                    // document.getElementById("res").innerHTML = curr_host + "/index.html";
                    // this.responseText;

                };

            }
            xhttp.open("POST", "/users", true); //переделать на users
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.send(regFromJData);
        }
        // function sendData() {
        //     let xhttp = new XMLHttpRequest();
        //     xhttp.onreadystatechange = function() {
        //         if (this.readyState == 4 && this.status == 200) {
        //             document.getElementById("info").innerHTML = this.responseText;
        //         } else if (this.status != 200) {
        //             alert("Something goes wrong");
        //         }
        //     };
        //     xhttp.open("GET", "info.txt?email=" + document.getElementById("email").value + "&password=" + document.getElementById("password").value, true);
        //     xhttp.send();
        // }