let vehicleNo = null;
let driverName = null;

$(document).on("click","#viewDtls", function(){
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
	            $("#nocExpdt").val(vehicle.nocExpiryDate);
	            $("#rcExpdt").val(vehicle.rcExpiryDate);
	            $("#licenseExpdt").val(vehicle.licenseExpiryDate);
	            $("#pucExpdt").val(vehicle.pucExpiryDate);
	            $("#insuranceExpdt").val(vehicle.insuranceExpiryDate);

	        },
	        error: function () {
	            alert("Error fetching vehicle");
	        }
	    });
	
});

$(document).on("click","#paymentDtls", function(){
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
