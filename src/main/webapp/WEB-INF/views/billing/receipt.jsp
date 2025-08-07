<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Receipt - PahanaEdu POS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        .receipt-container {
            max-width: 600px;
            margin: 20px auto;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .receipt-header {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            padding: 20px;
            border-radius: 8px 8px 0 0;
            text-align: center;
        }

        .receipt-body {
            padding: 20px;
        }

        .company-info {
            text-align: center;
            margin-bottom: 20px;
            border-bottom: 2px solid #eee;
            padding-bottom: 15px;
        }

        .bill-info {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-bottom: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 5px;
        }

        .items-table {
            margin: 20px 0;
        }

        .items-table th {
            background: #f8f9fa;
            font-weight: 600;
            border-bottom: 2px solid #dee2e6;
        }

        .items-table td {
            border-bottom: 1px solid #eee;
            padding: 8px;
        }

        .totals-section {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-top: 20px;
        }

        .total-row {
            display: flex;
            justify-content: space-between;
            margin: 5px 0;
        }

        .total-row.final {
            font-weight: bold;
            font-size: 1.1em;
            border-top: 2px solid #333;
            padding-top: 10px;
            margin-top: 10px;
        }

        .footer-info {
            text-align: center;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 2px dashed #ccc;
            color: #666;
            font-size: 0.9em;
        }

        .action-buttons {
            text-align: center;
            margin: 20px 0;
        }

        .no-print {
            display: block;
        }

        @media print {
            .no-print {
                display: none !important;
            }

            .receipt-container {
                box-shadow: none;
                border: none;
                margin: 0;
                max-width: none;
            }

            body {
                margin: 0;
                padding: 0;
            }
        }

        .status-badge {
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 0.8em;
            font-weight: 500;
        }

        .status-paid {
            background: #d4edda;
            color: #155724;
        }

        .status-pending {
            background: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body class="bg-light">
<div class="container-fluid">
    <!-- Navigation (no-print) -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary no-print">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-book-open"></i> PahanaEdu POS
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/billing">
                    <i class="fas fa-arrow-left"></i> Back to Billing
                </a>
            </div>
        </div>
    </nav>

    <!-- Success/Error Messages -->
    <c:if test="${param.success eq 'discount-applied'}">
        <div class="alert alert-success alert-dismissible fade show no-print" role="alert">
            <i class="fas fa-check-circle"></i> Discount applied successfully!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Receipt Container -->
    <div class="receipt-container">
        <!-- Receipt Header -->
        <div class="receipt-header">
            <h2><i class="fas fa-receipt"></i> RECEIPT</h2>
            <p class="mb-0">Thank you for your purchase!</p>
        </div>

        <!-- Receipt Body -->
        <div class="receipt-body">
            <!-- Company Information -->
            <div class="company-info">
                <h4>PahanaEdu Bookstore</h4>
                <p class="mb-1">12/A Amster Street, Soul Society</p>
                <p class="mb-1">Phone: (+94) 701234567 | Email: info@pahanaedu.com</p>
                <p class="mb-0">Website: www.pahanaedu.com</p>
            </div>

            <!-- Bill Information -->
            <div class="bill-info">
                <div>
                    <strong>Bill Number:</strong><br>
                    <span class="text-primary">${bill.billNumber}</span>
                </div>
                <div>
                    <strong>Date:</strong><br>
                    <fmt:formatDate value="${bill.createdAt}" pattern="MMM dd, yyyy HH:mm"/>
                </div>
                <div>
                    <strong>Customer:</strong><br>
                    <c:choose>
                        <c:when test="${not empty customer}">
                            <strong>Customer:</strong><br>
                            ${customer.name}<br>
                            Acc: ${customer.accountNumber}
                        </c:when>
                        <c:otherwise>
                            <strong>Customer:</strong><br>
                            Walk-in Customer
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <strong>Payment Status:</strong><br>
                    <span class="status-badge ${bill.paymentStatus eq 'PAID' ? 'status-paid' : 'status-pending'}">
                            <i class="fas ${bill.paymentStatus eq 'PAID' ? 'fa-check-circle' : 'fa-clock'}"></i>
                            ${bill.paymentStatus}
                        </span>
                </div>
            </div>

            <!-- Items Table -->
            <div class="items-table">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Book Details</th>
                        <th class="text-center">Qty</th>
                        <th class="text-end">Unit Price</th>
                        <th class="text-end">Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${bill.billItems}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>
                                <strong>${item.bookTitle}</strong><br>
                                <small class="text-muted">ISBN: ${item.isbn}</small>
                            </td>
                            <td class="text-center">${item.quantity}</td>
                            <td class="text-end">
                                <fmt:formatNumber value="${item.unitPrice}" type="currency" currencySymbol="$"/>
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$"/>
                                <c:if test="${item.discountAmount gt 0}">
                                    <br><small class="text-success">
                                    -<fmt:formatNumber value="${item.discountAmount}" type="currency" currencySymbol="$"/>
                                </small>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Totals Section -->
            <div class="totals-section">
                <div class="total-row">
                    <span>Subtotal:</span>
                    <span><fmt:formatNumber value="${bill.subtotal}" type="currency" currencySymbol="$"/></span>
                </div>

                <c:if test="${bill.discountAmount gt 0}">
                    <div class="total-row text-success">
                        <span>Discount:</span>
                        <span>-<fmt:formatNumber value="${bill.discountAmount}" type="currency" currencySymbol="$"/></span>
                    </div>
                </c:if>

                <div class="total-row">
                    <span>Tax (10%):</span>
                    <span><fmt:formatNumber value="${bill.taxAmount}" type="currency" currencySymbol="$"/></span>
                </div>

                <div class="total-row final">
                    <span>TOTAL AMOUNT:</span>
                    <span><fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="$"/></span>
                </div>

                <c:if test="${not empty bill.paymentMethod}">
                    <div class="total-row" style="margin-top: 10px;">
                        <span>Payment Method:</span>
                        <span class="text-capitalize">${bill.paymentMethod}</span>
                    </div>
                </c:if>
            </div>

            <!-- Notes Section -->
            <c:if test="${not empty bill.notes}">
                <div class="mt-3">
                    <strong>Notes:</strong>
                    <p class="mb-0 text-muted">${bill.notes}</p>
                </div>
            </c:if>

            <!-- Footer Information -->
            <div class="footer-info">
                <p><strong>Thank you for shopping with PahanaEdu!</strong></p>
                <p class="mb-1">
                    <i class="fas fa-undo"></i> Return Policy: Items can be returned within 30 days with receipt
                </p>
                <p class="mb-1">
                    <i class="fas fa-phone"></i> Customer Service: (+94) 701122526
                </p>
                <p class="mb-0">
                    <i class="fas fa-globe"></i> Visit us online: www.pahanaedu.com
                </p>
            </div>
        </div>
    </div>

    <!-- Action Buttons (no-print) -->
    <div class="action-buttons no-print">
        <button onclick="window.print()" class="btn btn-primary btn-lg me-2">
            <i class="fas fa-print"></i> Print Receipt
        </button>
        <button onclick="downloadPDF()" class="btn btn-secondary btn-lg me-2">
            <i class="fas fa-download"></i> Download PDF
        </button>
        <a href="${pageContext.request.contextPath}/billing/pos" class="btn btn-success btn-lg me-2">
            <i class="fas fa-plus"></i> New Sale
        </a>
        <a href="${pageContext.request.contextPath}/billing" class="btn btn-outline-primary btn-lg">
            <i class="fas fa-list"></i> View All Bills
        </a>
    </div>

    <!-- Discount Application Form (no-print) -->
    <c:if test="${bill.paymentStatus eq 'PAID'}">
        <div class="card mt-4 no-print" style="max-width: 600px; margin: 20px auto;">
            <div class="card-header">
                <h5><i class="fas fa-percent"></i> Apply Additional Discount</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/billing/apply-discount" method="post">
                    <input type="hidden" name="billId" value="${bill.billNumber}">

                    <div class="row">
                        <div class="col-md-4">
                            <label for="discountType" class="form-label">Discount Type</label>
                            <select class="form-select" id="discountType" name="discountType" required>
                                <option value="">Select Type</option>
                                <option value="percentage">Percentage (%)</option>
                                <option value="fixed">Fixed Amount ($)</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="discountValue" class="form-label">Discount Value</label>
                            <input type="number" class="form-control" id="discountValue" name="discountValue"
                                   step="0.01" min="0" max="100" required>
                        </div>
                        <div class="col-md-4 d-flex align-items-end">
                            <button type="submit" class="btn btn-warning w-100">
                                <i class="fas fa-percent"></i> Apply Discount
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>

<script>
    // Auto-dismiss alerts after 5 seconds
    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // PDF Download functionality
    function downloadPDF() {
        const { jsPDF } = window.jspdf;
        const receipt = document.querySelector('.receipt-container');

        html2canvas(receipt, {
            scale: 2,
            useCORS: true,
            allowTaint: true
        }).then(canvas => {
            const imgData = canvas.toDataURL('image/png');
            const pdf = new jsPDF('p', 'mm', 'a4');

            const imgWidth = 210;
            const pageHeight = 295;
            const imgHeight = (canvas.height * imgWidth) / canvas.width;
            let heightLeft = imgHeight;

            let position = 0;

            pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;

            while (heightLeft >= 0) {
                position = heightLeft - imgHeight;
                pdf.addPage();
                pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                heightLeft -= pageHeight;
            }

            pdf.save('receipt-${bill.billNumber}.pdf');
        }).catch(error => {
            console.error('Error generating PDF:', error);
            alert('Error generating PDF. Please try printing instead.');
        });
    }

    // Discount form validation
    document.getElementById('discountType')?.addEventListener('change', function() {
        const valueInput = document.getElementById('discountValue');
        const type = this.value;

        if (type === 'percentage') {
            valueInput.max = '100';
            valueInput.placeholder = 'Enter percentage (0-100)';
        } else if (type === 'fixed') {
            valueInput.max = '${bill.subtotal}';
            valueInput.placeholder = 'Enter amount';
        }
    });

    // Print-specific styling
    window.addEventListener('beforeprint', function() {
        document.body.style.margin = '0';
        document.body.style.padding = '0';
    });

    window.addEventListener('afterprint', function() {
        document.body.style.margin = '';
        document.body.style.padding = '';
    });
</script>
</body>
</html>