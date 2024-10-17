/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: true,
    eslint: {
        ignoreDuringBuilds: true,
    },
    experimental: {
        reactCompiler: true,
        turbo: {
            rules: {
                "*.scss": {
                    loaders: ["sass-loader"],
                    as: "*.css",
                },
            }
        },
    },
    images: {
        minimumCacheTTL: 6000,
        dangerouslyAllowSVG: true,
        remotePatterns: [
            {
                protocol: 'https',
                hostname: 'cdn.builder.io',
            },
            {
                protocol: 'https',
                hostname: 'placehold.co',
            },
        ],
    }
};

export default nextConfig;
