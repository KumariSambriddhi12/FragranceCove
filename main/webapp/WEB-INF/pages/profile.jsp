<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile | BITCOINT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="profile-container">
        <div class="sidebar">
            <h2 class="sidebar-title">BITCOINT</h2>
            <ul class="nav-menu">
                <li class="nav-item active">My profile</li>
                <li class="nav-item">Cards & Wallets</li>
                <li class="nav-item">Exchange currency</li>
                <li class="nav-item">Deposit funds</li>
            </ul>
            
            <div class="verification-banner">
                <span class="verification-icon">!</span>
                Account verification pending
            </div>
        </div>
        
        <div class="main-content">
            <h1 class="profile-title">Personal details</h1>
            
            <div class="profile-details">
                <div class="detail-group">
                    <label>FIRST name</label>
                    <div class="detail-value">Denys</div>
                </div>
                
                <div class="detail-group">
                    <label>Date of birth</label>
                    <div class="birth-date">
                        <div class="date-part">
                            <div class="date-label">Date</div>
                            <div class="date-value">23</div>
                        </div>
                        <div class="date-part">
                            <div class="date-label">Month</div>
                            <div class="date-value">August</div>
                        </div>
                        <div class="date-part">
                            <div class="date-label">Year</div>
                            <div class="date-value">1987</div>
                        </div>
                    </div>
                </div>
                
                <div class="detail-group">
                    <label>Last name</label>
                    <div class="detail-value">Seroushk</div>
                </div>
                
                <div class="detail-group">
                    <label>Phone number</label>
                    <div class="detail-value">+44</div>
                </div>
                
                <div class="detail-group">
                    <label>Country</label>
                    <div class="detail-value">Country.com</div>
                </div>
            </div>
            
            <div class="copyright-notice">
                Images may be subject to copyright. <a href="#" class="learn-more">Learn More</a>
            </div>
        </div>
    </div>
</body>
</html>