import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import Script from 'next/script';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
    title: 'Consafe',
    description: 'Contract Safely',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="ko">
            <body className={inter.className}>{children}</body>
            <Script src="https://cdn.jsdelivr.net/npm/tesseract.js@4/dist/tesseract.min.js"></Script>
        </html>
    );
}
