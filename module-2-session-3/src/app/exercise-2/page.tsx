import React from "react";
import Image from "next/image";
import "./style.scss";

interface ArticleCardProps {
    imageSrc: string;
    author: string;
    title: string;
    description: string;
    date: string;
    comments?: number;
}

const ArticleCard: React.FC<ArticleCardProps> = ({imageSrc, author, title, description, date, comments}) => (
    <article className="article-card">
        <div className="image">
            <Image src={imageSrc} alt={title} fill/>
        </div>
        <div className="content">
            <h3 className="title">{title}</h3>
            <p className="description">{description}</p>
            <div className="meta">
                <span className="date">{date}</span>
                {comments !== undefined && <span className="comments">{comments} Comments</span>}
                <span className="author">{author}</span>
            </div>
        </div>
    </article>
);


interface PopularWorkProps {
    imageSrc: string;
    title: string;
    description: string;
}

const PopularWork: React.FC<PopularWorkProps> = ({imageSrc, title, description}) => (
    <article className="popular-work">
        <div className="image">
            <Image src={imageSrc} alt={title} fill/>
        </div>
        <div className="content">
            <h3 className="title">{title}</h3>
            <p className="description">{description}</p>
        </div>
    </article>
);

const Page: React.FC = () => {
    const popularWorks = [
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/dea730aa6fe7b64fb27fe017454091ae06fa2d73396d50f73583288a00be39e6?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "Orange Pattern",
            description: "São Paulo, Brazil by Estúdio Bloom on Unsplash",
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/261a8b69eb4e95643b052f028910bdd41d0be22952602ce6370148fc7592946d?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "Blue Pattern",
            description: "São Paulo, Brazil by Estúdio Bloom on Unsplash",
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/f8ad0ec7e75a2ada936528c57176e1ef98d75147627c0bf8ea8940be9f92516a?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "Lemon Pattern",
            description: "São Paulo, Brazil by Estúdio Bloom on Unsplash",
        },
    ];

    const submittedArticles = [
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/fec71edd9edbf4dcec1db35d9ccbb13850ffed598f8beb0c2c3d76ca99efaa14?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "Design Process for Beginners",
            description: "In my opinion, Ui/Ux design is the foundation of a product, its face and soul. You can create an infinitely high-quality heart, and organize the simulation of breathing, but we won't fall in love with a product just because its heart beats in an interesting rhythm or its breathing smells like mint.",
            author: "JOSHUA COLEMAN",
            date: "Apr 8, 2023",
            comments: 6,
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/db0e9a986bf3b0a82e34585f9b18bed2cf50febaec701cb81f82d792d178c922?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "User-centered design — a brief guide",
            description: "As is commonly believed, this methodology places the user at the center of the world and focuses on their views and habits. In fact, the product's actual growth revolves around the persona for which the system is built.",
            author: "Ferdinand Stöhr",
            date: "Apr 8, 2023",
            comments: 4,
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/76c6b0c58b400678de8b883335e02c816842dd3b5847a563ebe8253e9acd3e16?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51",
            title: "Cognitive biases in UX design",
            description: `Cognitive bias (also known as "cognitive illusion" or "cognitive distortion") refers to errors in thinking that can lead to incorrect perception and decision-making. They are an inherent part of our psychology and can affect our behavior, even if we are not aware of them.`,
            author: "Maarten Deckers",
            date: "Apr 8, 2023",
            comments: 5,
        },
    ];


    const featuredArticles = [
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/2432f7600768efa105276e7b59ab955553350e5f7cf212a423d2fba9876e31e3?apiKey=e946ad7206da4373a899cf38e79aca51&",
            date: "Apr 8, 2023",
            author: "Maksym Ostrozhynskyy",
            title: "Pink stairs leading to the sky",
            description: "In my opinion, Ui/Ux design is the foundation of a product, its face and soul. You can create an infinitely high-quality heart, and organize the simulation of breathing, but we won't fall in love with a product just because its heart beats [...]"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/7e5dca2082b135b7eca276faff504635c9dc29c4b2c6d07e34e5ee2cf61457fe?apiKey=e946ad7206da4373a899cf38e79aca51&",
            date: "Apr 8, 2023",
            author: "Mike Yukhtenko",
            title: "Building on the corner of the sea",
            description: "Cognitive bias (also known as \"cognitive illusion\" or \"cognitive distortion\") refers to errors in thinking that can lead to incorrect perception and decision-making. They are an inherent part of our psychology and can affect [...]"
        },
        {
            imageSrc: "https://cdn.builder.io/api/v1/image/assets/TEMP/21541a8535b74892fcc448fbc0e47f753b3192d71ab1dcb4ab80c5596ebbd843?apiKey=e946ad7206da4373a899cf38e79aca51&",
            date: "Apr 8, 2023",
            author: "Estúdio Bloom",
            title: "The color of the sun for breakfast",
            description: "As is commonly believed, this methodology places the user at the center of the world and focuses on their views and habits. In fact, the product's actual growth revolves around the persona for which the system is built [...]"
        }
    ]

    return (
        <div className="exercise-2">
            <header className="hero">
                <nav className="navigation">
                    <div className="logo image">
                        <Image
                            src="https://cdn.builder.io/api/v1/image/assets/TEMP/7fa9cee4f559c32f18c3682da3ad9dbf04486adac851290aae42bc804b0b475a?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                            alt="Logo" fill/>
                    </div>
                    <ul className="menu">
                        <li><a href="#home">Homepage</a></li>
                        <li><a href="#news">News</a></li>
                        <li><a href="#about">About us</a></li>
                        <li><a href="#contacts">Contacts</a></li>
                    </ul>
                    <div className="search image">
                        <Image
                            src="https://cdn.builder.io/api/v1/image/assets/TEMP/8ed77451d906946c8ed91607706ce6099b590ab194fb6698b8193236083415f4?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                            alt=""
                            fill
                        />
                    </div>
                    <button className="cta-button">
                        Get Articler
                    </button>
                </nav>
                <div className="hero-content">
                    <div className="text-content">
                        <h1>Submit your article and join our network!</h1>
                        <p>{`Don't waste time and join our community of authors! Share your knowledge and experience with
                            our readers and get the opportunity to become a part of our professional and creative
                            team!`}</p>
                        <button className="submit-button">
                            <div className="image">
                                <Image
                                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/5f1813623d955c2816bd19c72a725ee87ae672b8f94dbf786e0c309e1defb146?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                    alt="" fill/>
                            </div>
                            Submit Article
                        </button>
                    </div>
                    <div className="image-grid">
                        <div className="image">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/1d80cc4186ce797c83c27c23544daaf2d3896fe640327b928b3304fe78eefade?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                alt="Article image by Guzmán Barquín on Unsplash" fill/>
                        </div>
                        <div className="image">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/e5ad58793cb534741dc7610e1b806093aac341c66c91a9d163c8c92207fc37e0?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                alt="Article image by Ryan Spencer on Unsplash" fill/>
                        </div>
                        <div className="image">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/14c0c99a3fe90ebbac09f91317b0a6d05c4cf0a0411491308e3813d88fbae1a9?placeholderIfAbsent=true&apiKey=e946ad7206da4373a899cf38e79aca51"
                                alt="Article image by S O C I A L . C U T on Unsplash" fill/>
                        </div>
                    </div>
                </div>
            </header>

            <main>
                <section className="popular-works">
                    <div className="header">
                        <h2>Popular works</h2>
                        <div className="image">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/50b9d20c33edafbb9f20ba056396f04ef23afb5f14b129867685cc34dc59bae1?apiKey=e946ad7206da4373a899cf38e79aca51&"
                                alt=""
                                fill
                            />
                        </div>
                    </div>
                    <div className="works-grid">
                        {popularWorks.map((work, index) => (
                            <PopularWork key={index} {...work} />
                        ))}
                    </div>
                </section>

                <section className="categories">
                    <ul>
                        <li><a href="#all">All Categories</a></li>
                        <li><a href="#news">News</a></li>
                        <li><a href="#personal">Personal Advices</a></li>
                        <li><a href="#travel">Travel</a></li>
                        <li><a href="#self-dev">Self-Development</a></li>
                        <li><a href="#funny">Funny Situations</a></li>
                        <li><a href="#natural">Natural</a></li>
                    </ul>
                </section>

                <section className="featured-article">
                    {submittedArticles.map((article, index) => (
                        <ArticleCard key={index} {...article} />
                    ))}
                </section>

                <section className="submitted-articles">
                    <div className="header">
                        <h2>Submitted Articles</h2>
                        <div className="image">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/50b9d20c33edafbb9f20ba056396f04ef23afb5f14b129867685cc34dc59bae1?apiKey=e946ad7206da4373a899cf38e79aca51&"
                                alt=""
                                fill
                            />
                        </div>
                    </div>
                    <div className="articles-grid">
                        {submittedArticles.map((article, index) => (
                            <ArticleCard key={index} {...article} />
                        ))}
                    </div>
                </section>
            </main>

            <footer>
                <div className="subscribe">
                    <p>Hello! If you have some questions, then you can just <a href="mailto:contact@articler.com">write
                        an email</a> to us ;)</p>
                </div>
                <div className="footer-content">
                    <p>&copy; 2023 Articler. Some rights reserved</p>
                    <nav>
                        <ul>
                            <li><a href="#home">Homepage</a></li>
                            <li><a href="#news">News</a></li>
                            <li><a href="#about">About us</a></li>
                            <li><a href="#contacts">Contacts</a></li>
                        </ul>
                    </nav>
                </div>
            </footer>
        </div>
    );
};

export default Page;
