import React from "react";
import Image from "next/image";
import "./style.scss";


type ProductCardProps = {
    imageSrc: string;
    colorCount: string;
    productName: string;
};

const ProductCard: React.FC<ProductCardProps> = ({imageSrc, colorCount, productName}) => (
    <article className="product-card">
        <div className="image">
            <Image src={imageSrc} alt={productName} fill/>
        </div>
        <div className="product-info">
            <p className="color-count">{colorCount}</p>
            <h3 className="product-name">{productName}</h3>
        </div>
    </article>
);

const Page: React.FC = () => {
    const navItems: NavItemProps[] = [
        {text: "Women"},
        {text: "Men"},
        {text: "Children"},
        {text: "Sale"},
        {text: "New In"},
        {text: "Home collection"},
    ];

    const newArrivals: ProductCardProps[] = [
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/cd1b561bd6c8e7bac254eafea2ff9fd2a0ef526c1de964898200e180b3643a9e?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "4 COLORS",
            productName: "Terry tote bag"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/48c3965f07a8953ca10b5289edcb873bb544122987969b45f51794be93d324ff?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "8 COLORS",
            productName: "Re-Nylon bucket hat"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/549cfef78200fab3e3e94863a3b25b5bc9acb36507a665cb1077498044e1f77e?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "1 COLOR",
            productName: "Prada Symbole sunglasses"
        },
    ];

    const newArrivalsForHim: ProductCardProps[] = [
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/21329f5a14b57ee097f75487df9c9e196889b85f630eb782647c7da4a2c5fc69?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "2 COLORS",
            productName: "Cotton pique T-shirt"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/cbb3333c7e051a5a7df4a630fcbbeadc6976437cd715502263fc0e6297939512?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "1 COLORS",
            productName: "Cotton robe"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/054efae3ce046f9efe9d24e3a9bd7020185fa27082c5f1b9a25cf211791662ba?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            colorCount: "2 COLOR",
            productName: "Prada Symbole sunglasses"
        },
    ];

    return (
        <div className="exercise-1">
            <header className="header">
                <div className="top-banner">
                    <div className="banner-content">
                        <div className="sale-message">
                            <p>SHOP THE SALE COLLECTION</p>
                        </div>
                        <div className="user-actions">
                            <button className="sign-in">
                                Sign In / Register
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/7f00ace640dfc3008164ac8b1fbaf81cf9f441894d7b93b163fc25736cdca1b4?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={15} height={15}/>
                            </button>
                            <button className="bag">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/5f5ccdaa586f5858febd198e73ff2ced02bda1c00382244e2c567dc3f83036a4?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={15} height={15}/>
                                Bag
                            </button>
                        </div>
                    </div>
                </div>
            </header>

            <main>
                <h1 className="logo">PRADA</h1>

                <nav className="main-nav">
                    <ul className="nav-list">
                        {navItems.map((item, index) => (
                            <li key={index} className="nav-item">
                                {item.text}
                            </li>
                        ))}
                    </ul>
                </nav>

                <section className="hero">
                    <div className="image">
                        <Image
                            src="https://cdn.builder.io/api/v1/image/assets/TEMP/bcc57551b2bfbfdfe909059ad03bd81e95359ef295454cd2bb5811a793e7c2e5?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                            alt="Prada Summer Collection" fill/>
                    </div>
                </section>

                <section className="summer-collection">
                    <h2 className="collection-title">SUMMER 2021</h2>
                    <p className="collection-description">
                        Explore sports-inspired ready-to-wear and bold accessories from the latest collection.
                    </p>
                    <a href="#" className="cta-button">NEW IN FOR HER</a>
                </section>

                <section className="new-arrivals">
                    <h2 className="section-title">NEW ARRIVALS</h2>
                    <div className="product-grid">
                        {newArrivals.map((product, index) => (
                            <ProductCard key={index} {...product} />
                        ))}
                    </div>
                </section>

                <section className="light-layers">
                    <div className="content">
                        <h2 className="section-title">LIGHT LAYERS</h2>
                        <p className="description">
                            The Pre-Fall 2021 womenswear collections lightweight jackets in relaxed fits.
                        </p>
                        <a href="#" className="cta-button">SHOP NOW</a>
                    </div>
                    <div className="image">
                        <Image
                            src="https://cdn.builder.io/api/v1/image/assets/TEMP/6d8162bb18256f7f4fdd76ddfc029a29c1d1eff64eee40d175da155d138f1e5d?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                            alt="Light Layers Collection" fill/>
                    </div>
                </section>

                <section className="new-arrivals-men">
                    <h2 className="section-title">NEW ARRIVALS FOR HIM</h2>
                    <div className="product-grid">
                        {newArrivalsForHim.map((product, index) => (
                            <ProductCard key={index} {...product} />
                        ))}
                    </div>
                </section>

                <section className="la-pradina">
                    <div className="image">
                        <Image
                            src="https://cdn.builder.io/api/v1/image/assets/TEMP/f61d20961ab4cc37917dedff95d0736d78eae79152d15e16a164dd7d763a345a?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                            alt="La Pradina Collection" fill/>
                    </div>
                    <div className="content">
                        <h2 className="section-title">LA PRADINA</h2>
                        <a href="#" className="cta-button">EVERYTHING FOR HER</a>
                    </div>
                </section>
            </main>

            <footer className="footer">
                <div className="footer-content">
                    <div className="company-info">
                        <h3>COMPANY</h3>
                        <ul>
                            <li>Fondazione Prada</li>
                            <li>Prada Group</li>
                            <li>Luna Rossa</li>
                            <li>Sustainability</li>
                            <li>Work with us</li>
                        </ul>
                        <button className="store-locator">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/ceb859889b7acc0d0fef8b2c06fe14920f7f15cfb1f3b596dbf3f00229f468b0?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                alt="" width={15} height={15}/>
                            Store Locator
                        </button>
                    </div>
                    <div className="legal-info">
                        <h3>LEGAL TERMS AND CONDITIONS</h3>
                        <ul>
                            <li>Legal Notice</li>
                            <li>Privacy Policy</li>
                            <li>Cookie Policy</li>
                            <li>Sitemap</li>
                        </ul>
                    </div>
                    <div className="social-media">
                        <h3>FOLLOW US</h3>
                        <div className="social-icons">
                            <a href="#" aria-label="Facebook">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/5dfb5b8ecc476937cb71475581e6cf8a9c7667fd30cf140032249a1414e66b09?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={20} height={20}/>
                            </a>
                            <a href="#" aria-label="Twitter">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/d995dc26b727b3a699920bb82f6b7562a354a61c23ea473ba9b8b7de1191233c?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={20} height={20}/>
                            </a>
                            <a href="#" aria-label="Instagram">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/545a2e716b347ed2ff61b60a8f296426d2fc9537779340a58d77a390be899e7d?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={20} height={20}/>
                            </a>
                            <a href="#" aria-label="YouTube">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/f03b590ba9f77c517dc38c96192b5e0a57541b902b26b41e45563f94bcdafda5?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" width={20} height={20}/>
                            </a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    );
};

export default Page;
