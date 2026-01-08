$(document).ready(function () {

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const reminderMap = {};

    $(".vehicle-card").each(function () {

        const vehicleNo = $(this).data("vehicleno");
        const driverName = $(this).data("driver");

        const docs = [
            { type: "FC", date: $(this).data("fc-exp"), key: vehicleNo },
            { type: "RC", date: $(this).data("rc-exp"), key: vehicleNo },
            { type: "NOC", date: $(this).data("noc-exp"), key: vehicleNo },
            { type: "PUC", date: $(this).data("puc-exp"), key: vehicleNo },
            { type: "INSURANCE", date: $(this).data("insurance-exp"), key: vehicleNo },
            { type: "LICENSE", date: $(this).data("license-exp"), key: driverName }
        ];

        docs.forEach(doc => {
            if (!doc.date) return;

            const expDate = new Date(doc.date);
            expDate.setHours(0, 0, 0, 0);

            const diffDays = Math.ceil(
                (expDate - today) / (1000 * 60 * 60 * 24)
            );

            if (diffDays <= 30) {

                if (!reminderMap[doc.key]) {
                    reminderMap[doc.key] = [];
                }

                reminderMap[doc.key].push({
                    type: doc.type,
                    date: doc.date
                });
            }
        });
    });

    renderReminders(reminderMap);
});

function renderReminders(reminderMap) {

    const $container = $("#reminderContainer");
    $container.empty();

    if (Object.keys(reminderMap).length === 0) {
        $container.append(`
            <div class="reminder empty">
                No upcoming expiries
            </div>
        `);
        return;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    Object.keys(reminderMap).forEach(key => {

        const records = reminderMap[key];
        const isLicense = records[0].type === "LICENSE";

        const $group = $(`
            <div class="reminder-group">
                <div class="reminder-title">
                    ${isLicense ? "Driver" : "Vehicle"}: ${key}
                </div>
            </div>
        `);

        records.forEach(r => {

            const expiryDate = new Date(r.date);
            expiryDate.setHours(0, 0, 0, 0);

            const diffDays = Math.ceil(
                (expiryDate - today) / (1000 * 60 * 60 * 24)
            );

            let statusClass = "";
            let statusText = "expires on";

            if (diffDays < 0) {
                statusClass = "expired";
                statusText = "EXPIRED on";
            } else if (diffDays <= 7) {
                statusClass = "expiring";
                statusText = "expiring on";
            }

            $group.append(`
                <div class="reminder-item ${statusClass}">
                    <b>${r.type}</b> ${statusText}
                    <span>${formatDate(r.date)}</span>
					${diffDays > 0 ? ` (${diffDays} days left)` : diffDays === 0 ? " ( Today )" : ""}
                </div>
            `);
        });

        $container.append($group);
    });
}




function formatDate(dateStr) {
    if (!dateStr) return "";
    const [year, month, day] = dateStr.split("-");
    return `${day}-${month}-${year}`;
}

$(document).on("click","#addVehicle", function(){
	
	$("#vehicleNo").val(''),
	$("#category").val(''),
	$("#driverName").val(''),
	$("#driverPhoneNo").val(''),
	$("#fcExpdt").val(''),
	$("#nocExpdt").val(''),
	$("#rcExpdt").val(''),
	$("#licenseExpdt").val('')
	$("#insuranceExpdt").val('')
	$("#pucExpdt").val('')
	$("#fcFileName").text("");
    $("#nocFileName").text("");
    $("#rcFileName").text("");
    $("#licenseFileName").text("");
    $("#aadharFileName").text("");
    $("#pucFileName").text("");
    $("#insuranceFileName").text("");
	$('.download-btn').hide();
	$(".editContainer").show();
	$("#heading").text("ADD NEW VEHICLE");
	$(".error-text").text("");
	
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

const fieldConfig = [
    { id: "#vehicleNo", msg: "Vehicle number is required" },
    { id: "#category", msg: "Please select category" },
    { id: "#driverName", msg: "Driver name is required" },
    { id: "#driverPhoneNo", msg: "Driver phone number is required" },

    { id: "#fcExpdt", msg: "FC expiry date is required" },
    { id: "#nocExpdt", msg: "NOC expiry date is required" },
    { id: "#rcExpdt", msg: "RC expiry date is required" },
    { id: "#licenseExpdt", msg: "License expiry date is required" },
    { id: "#pucExpdt", msg: "PUC expiry date is required" },
    { id: "#insuranceExpdt", msg: "Insurance expiry date is required" },

    /* FILE UPLOADS (MANDATORY) */
    { id: "#uploadFcFile", msg: "FC document is required", type: "file" },
    { id: "#uploadNocFile", msg: "NOC document is required", type: "file" },
    { id: "#uploadRcFile", msg: "RC document is required", type: "file" },
    { id: "#uploadLicenseFile", msg: "License document is required", type: "file" },
    { id: "#aadharFileName", msg: "Aadhar Card is required", type: "file" },
    { id: "#pucFileName", msg: "PUC is required", type: "file" },
    { id: "#insuranceFileName", msg: "Insurance is required", type: "file" }
];


// ---------------- INIT ----------------
//$(document).ready(function () {
//
//    // Inject error spans below inputs (NO HTML change)
//    fieldConfig.forEach(f => {
//        if (!$(f.id).next(".error-text").length) {
//            $(f.id).after('<span class="error-text"></span>');v
//        }
//    });
//
//    // Disable save initially
////    $("#saveVehicle").prop("disabled", true);
//
//    // Live validation
//    $("#modal input, #modal select").on("input change", validateForm);
//});

// ---------------- HELPERS ----------------
function showError($el, msg) {
    $el.addClass("invalid");
    $el.next(".error-text").text(msg);
}

function clearError($el) {
    $el.removeClass("invalid");
    $el.next(".error-text").text("");
}

function validateForm() {

    let isValid = true;

    // clear all previous errors
    $(".error-text").text("");
    $("input, select").removeClass("invalid");

    fieldConfig.forEach(field => {

        const $el = $(field.id);

        /* ================= FILE VALIDATION ================= */
        if (field.type === "file") {

            const $docCard = $el.closest(".doc-card");
            const $error = $docCard.find(".file-error");

            // get matching download button for this file
            const type = $docCard.find(".view-btn").data("type");
            const hasSavedFile = $('.download-btn[data-type="' + type + '"]').is(":enabled");

            // ADD mode → file required
            if (!$el[0].files?.length && !hasSavedFile) {
                isValid = false;
                $error.text(field.msg);
            }
            return;
        }

        /* ================= DATE VALIDATION ================= */
        if ($el.attr("type") === "date") {

            const $docCard = $el.closest(".doc-card");
            const $error = $docCard.find(".date-error");

            if (!$el.val()) {
                isValid = false;
                $el.addClass("invalid");
                $error.text(field.msg);
            }
            return;
        }

        /* ================= NORMAL INPUTS ================= */
        const $error = $el.closest(".form-group").find(".error-text");

        // category / text / number fields
        if (!$el.val()) {
            isValid = false;
            $el.addClass("invalid");
            $error.text(field.msg);
        }
    });

    return isValid;
}


$(document).on("click", "#saveVehicle", function () {
	
	fieldConfig.forEach(f => {
	        if (!$(f.id).next(".error-text").length) {
	            $(f.id).after('<span class="error-text"></span>');
	        }
	    });

	    // Disable save initially
	//    $("#saveVehicle").prop("disabled", true);

	    // Live validation
	    $("#modal input, #modal select").on("input change", validateForm);
	
	
	if (!validateForm()) {
	       return;
	   }
	
    let dto = {
        vehicleNo: $("#vehicleNo").val(),
        category: $("#category").val(),
        driverName: $("#driverName").val(),
        driverPhoneNo: $("#driverPhoneNo").val(),
        fcExpdt: $("#fcExpdt").val(),
        nocExpdt: $("#nocExpdt").val(),
        rcExpdt: $("#rcExpdt").val(),
        licenseExpdt: $("#licenseExpdt").val(),
        insuranceExpdt: $("#insuranceExpdt").val(),
        pucExpdt: $("#pucExpdt").val()
    };

    let formData = new FormData();

    formData.append(
        "vehicle",
        new Blob([JSON.stringify(dto)], { type: "application/json" })
    );

    formData.append("fcFile", $("#uploadFcFile")[0].files[0]);
    formData.append("nocFile", $("#uploadNocFile")[0].files[0]);
    formData.append("rcFile", $("#uploadRcFile")[0].files[0]);
    formData.append("licenseFile", $("#uploadLicenseFile")[0].files[0]);
    formData.append("aadharFile", $("#uploadAadharFile")[0].files[0]);
    formData.append("pucFile", $("#uploadPucFile")[0].files[0]);
    formData.append("insuranceFile", $("#uploadInsuranceFile")[0].files[0]);

    $.ajax({
        url: "/saveVehicle",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
	            alert("Saved successfully");
				$('.editContainer').hide();
				window.location.replace("/home");
        },
        error: function () {
            alert("Error saving vehicle");
        }
    });
});

$(document).on("click", ".vehicle-card", function () {
    let vehicleNo = $(this).data("vehicleno");
	$('.download-btn').show();
	$(".error-text").text("");
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

            $("#fcFileName").text(vehicle.fcFileName || "");
            toggleButtons("fc", vehicle.hasFc);

            $("#nocFileName").text(vehicle.nocFileName || "");
            toggleButtons("noc", vehicle.hasNoc);

            $("#rcFileName").text(vehicle.rcFileName || "");
            toggleButtons("rc", vehicle.hasRc);

            $("#licenseFileName").text(vehicle.licenseFileName || "");
            toggleButtons("license", vehicle.hasLicense);
            
			$("#insuranceFileName").text(vehicle.insuranceFileName || "");
            toggleButtons("insurance", vehicle.hasInsurance);
           
			 $("#aadharFileName").text(vehicle.aadharFileName || "");
            toggleButtons("aadhar", vehicle.hasAadhar);
           
			 $("#pucFileName").text(vehicle.pucFileName || "");
            toggleButtons("puc", vehicle.hasPuc);

            $(".editContainer").show();
            $("#heading").text("EDIT VEHICLE DETAILS");
        },
        error: function () {
            alert("Error fetching vehicle");
        }
    });
});

function toggleButtons(type, hasFile) {
    $('.view-btn[data-type="' + type + '"]').prop("disabled", !hasFile);
    $('.download-btn[data-type="' + type + '"]').prop("disabled", !hasFile);
}
	
$(document).on("click", ".view-btn", function () {

    const vehicleNo = $("#vehicleNo").val();
    const type = $(this).data("type");

    // Get corresponding file input
	const $fileInput = $("#upload" + type.charAt(0).toUpperCase() + type.slice(1) + "File");
    const file = $fileInput[0].files[0];

    // CASE 1: No uploaded file & no saved data
	if (!file && !$('.download-btn').is(':visible')) {
	    alert("No document uploaded to view");
	    return;
	}


    // CASE 2: ADD mode (file uploaded but not saved yet)
    if (file && !$('.download-btn').is(':visible')) {
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL, "_blank");
        return;
    }

    // CASE 3: EDIT mode (already saved in DB)
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

$(document).on('click','#closeVehicleDtls', function(){
	
	$('.editContainer').hide();
	
});

$("#uploadFcBtn").click(function () {
    $("#uploadFcFile").click();
});

$("#uploadFcFile").change(function () {
    $("#fcFileName").text(this.files[0]?.name || "");
});


$("#uploadNocBtn").click(function () {
    $("#uploadNocFile").click();
});

$("#uploadNocFile").change(function () {
    $("#nocFileName").text(this.files[0]?.name || "");
});


$("#uploadRcBtn").click(function () {
    $("#uploadRcFile").click();
});

$("#uploadRcFile").change(function () {
    $("#rcFileName").text(this.files[0]?.name || "");
});


$("#uploadLicenseBtn").click(function () {
    $("#uploadLicenseFile").click();
});

$("#uploadAadharBtn").click(function () {
    $("#uploadAadharFile").click();
});

$("#uploadPucBtn").click(function () {
    $("#uploadPucFile").click();
});

$("#uploadInsuranceBtn").click(function () {
    $("#uploadInsuranceFile").click();
});



$("#uploadLicenseFile").change(function () {
    $("#licenseFileName").text(this.files[0]?.name || "");
});

$("#uploadInsuranceFile").change(function () {
    $("#insuranceFileName").text(this.files[0]?.name || "");
});

$("#uploadPucFile").change(function () {
    $("#pucFileName").text(this.files[0]?.name || "");
});

$("#uploadAadharFile").change(function () {
    $("#aadharFileName").text(this.files[0]?.name || "");
});


$('#vehicleNo').on('input', function () {
    this.value = this.value.toUpperCase();
});

$('#driverName').on('input', function () {
    this.value = this.value.toUpperCase();
});
