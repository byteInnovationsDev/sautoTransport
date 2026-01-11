$('.billingDtl').on('change', function(){
	
	$('#inv-veh').text($(this).closest('.vehicle-item').data('vehicleno'));
//	$('#inv-amt').val($(this).closest('.vehicle-card').data('payamt')).prop('disabled', true);
	$('#inv-dri').text($(this).closest('.vehicle-item').data('driver'));
//	$('#inv-date').val($(this).closest('.vehicle-card').data('paydt')).prop('disabled', true);
});

$('.generate-btn').on('click', function () {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const card = $('.vehicle-item.selected');
    if (!card.length) {
        alert('Please select a vehicle');
        return;
    }

    const vehicleNo = card.data('vehicleno');
    const driver = card.data('driver');
	const rawDate = $('#inv-date').val();
	const [y, m, d] = rawDate.split('-');
	const date = `${d}-${m}-${y}`;
    const amount = Number($('#inv-amt').val()).toFixed(2);

    doc.setFont('helvetica', 'normal');

    // ===== HEADINGS =====
    doc.setFontSize(16);
    doc.text('INVOICE', 105, 20, { align: 'center' });

    doc.setFontSize(13);
    doc.text('S AUTO TRANSPORT', 105, 28, { align: 'center' });

    // ===== PAYEE INFO =====
    doc.setFontSize(11);
    doc.text(`Payee Name : ${driver}`, 20, 45);
    doc.text(`Invoice Date : ${date}`, 20, 55);

    // ===== TABLE =====
    const startY = 70;
    const rowHeight = 12;

    // Table border
    doc.rect(20, startY, 170, rowHeight * 2);

    // Header line
    doc.line(20, startY + rowHeight, 190, startY + rowHeight);

    // Column line
    doc.line(130, startY, 130, startY + rowHeight * 2);

    // Header text
    doc.setFont(undefined, 'bold');
    doc.text('Particulars', 22, startY + 8);
    doc.text('Amount (Rs.)', 150, startY + 8);

    // Row text
    doc.setFont(undefined, 'normal');
    doc.text(`Vehicle No : ${vehicleNo}`, 22, startY + 20);
    doc.text(amount, 150, startY + 20);

//    // ===== FOOTER =====
//    doc.text('Thank you', 105, startY + 50, {
//        align: 'center'
//    });

    doc.save(`Invoice_${vehicleNo}.pdf`);
});


$(document).on('change', '.billingDtl', function () {
    $('.vehicle-item').removeClass('selected');
    $(this).closest('.vehicle-item').addClass('selected');
});	

$('.vehicle-item').on('click', function () {
    $(this).find('.billingDtl').prop('checked', true).trigger('change');
});

$("#searchVehicle").on("input", function () {

    let searchText = $(this).val().toLowerCase().trim();

    $(".vehicle-item").each(function () {

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
	const visibleCount = $(".vehicle-card:visible").length;
	$("#no-records").toggle(visibleCount === 0);
});

$('.reportBtn').on('click', function () {

    let fromDate = $('#rptFrDt').val();
    let toDate = $('#rptToDt').val();
	
	if(!fromDate){
		alert("select From Date!");
		return;
	}else if(!toDate){
		alert("select To Date!")
		return;
	}

    window.location.href =
        "/report?fromDate=" + fromDate + "&toDate=" + toDate;
});

