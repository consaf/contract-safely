import Image from 'next/image';

interface Props {
    label: 'login' | 'logout';
    size?: 'sm' | 'md' | 'lg';
}

export default function NaverButton(props: Props) {
    const { label } = props;
    return (
        <>
            {label === 'login' ? (
                <Image src="naver_login.svg" width={120} height={40} alt="naver login" />
            ) : (
                <Image src="something" width={120} height={40} alt="naver logout" />
            )}
        </>
    );
}
