<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us | Aromance</title> <%-- Or your brand name --%>

    <%-- Link Common Styles First --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <%-- Link About Page Specific Styles --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutUs.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">

    <%-- Optional: Add Google Fonts (ensure it matches other pages) --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <%-- Include Header --%>
    <jsp:include page="header.jsp" />

    <main>
        <%-- Optional: Banner Section --%>
        <section class="about-banner section-padding">
            <div class="container">
                <h1>Our Story</h1>
                <p class="subtitle">Crafting elegance, one scent at a time.</p>
            </div>
        </section>

        <%-- Main Content Section --%>
        <section class="about-content section-padding">
            <div class="container">
                <div class="about-content-grid">
                    <div class="about-text">
                        <h2>Welcome to Aromance</h2>
                        <p>Founded with a passion for the art of perfumery, Aromance began as a dream to capture the essence of nature's most captivating aromas and transform them into luxurious sensory experiences. We believe that fragrance is more than just a scent; it's a form of self-expression, a memory trigger, and an invisible accessory that completes your presence.</p>
                        <p>Our journey started in [Year], sourcing the finest raw ingredients from sustainable sources around the globe. Each fragrance is meticulously blended by master perfumers, combining traditional techniques with modern innovation to create unique and long-lasting scents.</p>

                        <h3>Our Philosophy</h3>
                        <p>We are committed to quality, sustainability, and elegance. From the ethically sourced botanicals to the minimalist design of our packaging, every detail is thoughtfully considered. We strive to create products that not only delight the senses but also respect our planet.</p>

                        <a href="${pageContext.request.contextPath}/shop.jsp" class="btn btn-primary">Explore Our Collections</a>
                    </div>
                    <div class="about-image">
                        <%-- Replace with an actual relevant image --%>
                        <img src="${pageContext.request.contextPath}/images/aboutus/aboutus.png" alt="Artisanal perfume ingredients or workshop scene">
                    </div>
                </div>
            </div>
        </section>

    </main>

    <%-- Include Footer --%>
    <jsp:include page="footer.jsp" />

</body>
</html>