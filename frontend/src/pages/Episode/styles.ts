import styled from 'styled-components'

export const EpisodeCard = styled.div`
flex: 1;
display: flex;
margin: 0 auto;
max-width: 96%;
max-height: 25vh;

padding: 30px;
border-radius: 20px;

background: #292B2D;

justify-content: space-between;
align-items: center;

& + div {
    margin-top: 5vh;
}

`

export const EpisodeImageCard = styled.div`
width: 6vw;
height: 6vw;

background-image: url(${ props => props.about }), linear-gradient(270deg, #56525a 0%, rgba(25, 25, 26, 0) 100%);
background-position: center;
background-size: cover;
background-repeat: no-repeat;

backdrop-filter: blur(42px);
border-radius: 14px;
border: 5px solid #161719;
`

export const EpisodeInfo = styled.div`
display: flex;
flex-direction: column;
margin-left: 1vw;
justify-content: space-around;

font-family: 'DM Sans', sans-serif;
color: #fff;
font-size: 0.7vw;
font-weight: bold;
`

export const EpisodeStats = styled.div`
display: flex;

img {
    margin-right: 1.5vw;
    width: 1.7vw;
    height: 1.7vw;

    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;
}

h1 {
    margin-right: 3vw;
    font-family: 'DM Sans', sans-serif;
    color: #fff;
    font-size: 1.4vw;
    font-weight: bold;
}
`

export const Buttons = styled.div`
    display: flex;
    justify-content: space-around;
    align-items: center;
    min-width: 7vw;

    button {
        width: 1.9vw;
        height: 1.9vw;
        border: none;
        background: #292B2D;
    }
`
