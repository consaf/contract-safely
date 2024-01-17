'use client';

import Tesseract from 'tesseract.js';
import { useState } from 'react';
import ProgressBar from 'react-bootstrap/ProgressBar';

export default function UploadImage() {
    const [log, setLog] = useState({ status: 'default', progress: 0 });
    const [imagePath, setImagePath] = useState('');
    const [result, setResult] = useState('');

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files === null) return;

        const tempImagePath = URL.createObjectURL(event.target.files[0]);

        setImagePath(tempImagePath);

        Tesseract.recognize(tempImagePath, 'kor', {
            logger: (m) => {
                setLog({
                    status: m.status,
                    progress: Math.floor(parseFloat(m.progress.toFixed(2)) * 100),
                });
            },
        })
            .catch((err) => {
                console.error(err);
            })
            .then((result) => {
                const text = result?.data?.text || ''; // Optional chaining to handle undefined or null
                setResult(text);
            });
    };

    return (
        <main>
            <h1>계약서 이미지 업로드하기</h1>
            <img src={imagePath} className="upload_img" alt="upload_img" style={{ width: '400px', height: '400px' }} />
            <input type="file" onChange={handleChange} />
            <h3>인식 결과</h3>
            {'분류중 >>'} <ProgressBar label={`${log.progress}%`} now={log.progress}></ProgressBar>
            <div className="text-box">
                <p>result: {result}</p>
            </div>
        </main>
    );
}
