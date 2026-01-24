if (!sessionStorage.getItem("AUTH")) {
    window.location.href = "/logout";
  }
  
  let idleTime = 0;
    const MAX_IDLE_MINUTES = 60;

    function resetIdleTimer() {
      idleTime = 0;
    }

    // Events that count as activity
    window.onload = resetIdleTimer;
    document.onmousemove = resetIdleTimer;
    document.onkeydown = resetIdleTimer;
    document.onclick = resetIdleTimer;
    document.onscroll = resetIdleTimer;

    // Check every minute
    setInterval(() => {
      idleTime++;
      if (idleTime >= MAX_IDLE_MINUTES) {
        window.location.href = "/logout";
      }
    }, 60000);
	
let vehicleNo = null;
let driverName = null;

$(document).on("click",".viewDtls", function(){
	$("#viewModal").show();
	$("#heading").text("VEHICLE DETAILS");
	$('.modal-box').css({
	    background: '#e6e6e6',
	    width: '750px',
	    'border-radius': '12px',
	    padding: '25px',
	    position: 'relative',
	    top: '7%',
	    left: '28%',
	    height: '80%'
	});

	    let vehicleNo = $(this).closest(".vehicle-card").data("vehicleno");

		$.ajax({
		    url: "/findVehicle",
		    type: "GET",
		    data: { vehicleNo: vehicleNo },
		    success: function (vehicle) {

		        $("#vehicleNo").val(vehicle.vehicleNo);
		        $("#category").val(vehicle.category);
		        $("#driverName").val(vehicle.driverName);
		        $("#driverPhoneNo").val(vehicle.driverPhoneNo);

		        $("#fcExpdt").val(vehicle.fcExpiryDate);
		        $("#rcExpdt").val(vehicle.rcExpiryDate);
		        $("#insuranceExpdt").val(vehicle.insuranceExpiryDate);
		        $("#pucExpdt").val(vehicle.pucExpiryDate);

		        // ================= OPTIONAL DOCUMENTS =================

		        // NOC
		        if (vehicle.nocExpiryDate) {
		            $("#nocExpdt").val(vehicle.nocExpiryDate);
		            $("#nocRow").show();
		        } else {
		            $("#nocRow").hide();
		        }

		        // Aadhar Card
		        if (vehicle.aadharFileName || vehicle.aadharExpiryDate) {
		            $("#aadharRow").show();
		        } else {
		            $("#aadharRow").hide();
		        }

		        // Driver License
		        if (vehicle.licenseExpiryDate) {
		            $("#licenseExpdt").val(vehicle.licenseExpiryDate);
		            $("#licenseRow").show();
		        } else {
		            $("#licenseRow").hide();
		        }
		    },
		    error: function () {
		        alert("Error fetching vehicle");
		    }
		});

	
});

$(document).on('click', '.paymentDtls', function () {
	$("#paymentModal").show();
	$('.modal-box').css({
	    background: '#e6e6e6',
	    width: '700px',
	    'border-radius': '12px',
	    padding: '25px',
	    position: 'relative',
	    top: '14%',
	    left: '28%',
	    height: '45%'
	});

	const today = new Date().toISOString().split('T')[0];
	$("#paymentDate").val(today);
	vehicleNo = $(this).data("vehicleno");
	driverName = $(this).data("driver");
});

$(document).on("click", "#savePayment", function () {

    $.ajax({
        url: "/savePayment",
        type: "POST",
        data: {
            vehicleNo: vehicleNo,
            driverName: driverName,
            paymentDate: $("#paymentDate").val(),
            paymentAmount: $("#paymentAmt").val()
        },
        success: function () {
            alert("Payment saved successfully");
            $("#paymentModal").hide();
        },
        error: function () {
            alert("Error saving payment");
        }
    });
});


$(document).on("click", ".view-btn", function () {
    let vehicleNo = $("#vehicleNo").val();
    let type = $(this).data("type");

    window.open(
        "/vehicle/" + vehicleNo + "/document/" + type,
        "_blank"
    );
});

$(document).on("click", ".download-btn", function () {
    let vehicleNo = $("#vehicleNo").val();
    let type = $(this).data("type");

    window.location.href =
        "/vehicle/" + vehicleNo + "/document/" + type + "/download";
});

$("#searchVehicle").on("input", function () {

    let searchText = $(this).val().toLowerCase().trim();

    $(".vehicle-card").each(function () {

        let vehicleNo = $(this).data("vehicleno").toLowerCase();
        let driverName = $(this).data("driver").toLowerCase();

        if (
            vehicleNo.includes(searchText) ||
            driverName.includes(searchText)
        ) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
});

$(document).on('click', '.deleteDtls', function () {
	
	let vehicleNum = $(this).data('vehicleno');
	
	if (confirm("Are you sure you want to Delete the Vehicle"+ vehicleNum +"?")) {
		$.ajax({
			        url: "/deleteVehicle",
			        type: "POST",
			        data: {
			            vehicleNo: vehicleNum
			        },
			        success: function (data) {
						
						if(data === "success"){
			            	alert("Vehicle Deleted successfully");
							location.reload();
						}else{
							alert("Error Deleting the Vehicle");
						}
			        },
			        error: function () {
			            alert("Error deleting the Vehicle");
			        }
			    });
	} else {
	    return false;
	}
	
});
