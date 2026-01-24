$(document).on("click", "#loginBtn", function () {

    let userId = $("#userId").val();
    let userPass = $("#userPass").val();

    $.ajax({
        url: "/login",
        type: "POST",
        data: {
            userId: userId,
            userPass: userPass
        },
        success: function (response) {

            if (response === "invalid") {
                alert("Invalid username or password");
            
				} else if (response === "success") {
					sessionStorage.setItem("AUTH", "true");
	                window.location.href = "/home";
            }
        },
        error: function () {
            alert("Error calling server");
        }
    });
});
