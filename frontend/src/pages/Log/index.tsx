import React, { useEffect, useState } from 'react'
import { Bar } from 'react-chartjs-2'
import Header from '../../Components/Header'
import api from '../../services/api'

interface IView {
    pod_id: string;
    nAcessos: number;
    pod_nome: string;
}

interface IAge {
    pod_id: string;
    avg_idade: number;
    pod_nome: string;
}

interface IViewT {
    pod_id: string;
    nAcessos: number;
}

interface IAgeT {
    tag: string;
    avg_idade: number;
}

const Log: React.FC = () => {
    const [views, setViews] = useState<IView[]>([])
    const [ages, setAges] = useState<IAge[]>([])

    const [viewsT, setViewsT] = useState<IViewT[]>([])
    const [agesT, setAgesT] = useState<IAgeT[]>([])


    useEffect(() => {
        const getViews = async () => {
            const response = await api.get('/logAcesso/podcastsMaisVistos')

            setViews(response.data.acessos.slice(0, 7))
            return response.data.acessos.slice(0, 7)
        }

        getViews()
    }, [])

    useEffect(() => {
        const getAges = async () => {
            const response = await api.get('/logAcesso/mediaIdadesPodcast')
            const aux = []

            for(let i = 0; i < views.length; i++) {
                aux.push(response.data.medias
                    .filter(
                        (media: IAge) => views[i].pod_id === media.pod_id
                    )[0])
            }

            setAges(aux)
        }

        getAges()
    }, [views])

    useEffect(() => {
        const getViewsT = async () => {
            const response = await api.get('/logAcesso/tagsMaisVistas')

            setViewsT(response.data.acessos.slice(0, 7))
            return response.data.acessos.slice(0, 7)
        }

        getViewsT()
    }, [])

    useEffect(() => {
        const getAgesT = async () => {
            const response = await api.get('/logAcesso/mediaIdadesTag')
            const aux = []

            for(let i = 0; i < viewsT.length; i++) {
                aux.push(response.data.medias
                    .filter(
                        (media: IAgeT) => viewsT[i].pod_id === media.tag
                    )[0])
            }

            setAgesT(aux)
        }

        getAgesT()
    }, [viewsT])

    return (
        <>
            <Header/>
            <div>
                {
                    (views.length > 0 && ages.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: views.map((view) => view.pod_nome),
                                        datasets: [{
                                            label: 'Podcasts Mais acessados',
                                            data: views.map(view => view.nAcessos),
                                            backgroundColor: [
                                                'rgb(255, 72, 109)',
                                                'rgb(54, 163, 235)',
                                                'rgb(255, 207, 86)',
                                                'rgb(75, 192, 192)',
                                                'rgb(153, 102, 255)',
                                                'rgb(2, 255, 44)',
                                                'rgb(255, 2, 2)',
                                            ],
                                            borderWidth: 0,
                                        }, {
                                            label: 'Idade média dos usuários que acessam',
                                            data: ages.map(age => age.avg_idade.toFixed(2)),
                                            backgroundColor: [
                                                'rgb(250, 0, 208)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 145, 0)',
                                                'rgb(0, 255, 157)',
                                                'rgb(140, 0, 255)',
                                                'rgb(145, 255, 2)',
                                                'rgb(255, 82, 2)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (views[0].nAcessos + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (viewsT.length > 0 && agesT.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: viewsT.map((view) => view.pod_id),
                                        datasets: [{
                                            label: 'Tags Mais acessadas',
                                            data: viewsT.map(view => view.nAcessos),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                            ],
                                            borderWidth: 0,
                                        }, {
                                            label: 'Idade média dos usuários que acessam as Tags',
                                            data: agesT.map(age => age.avg_idade.toFixed(2)),
                                            backgroundColor: [
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                                'rgb(250, 0, 208)',
                                                'rgb(145, 255, 2)',
                                                'rgb(0, 255, 157)',
                                                'rgb(255, 145, 0)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (viewsT[0].nAcessos + 10) } }] } }}
                        />
                    )
                }
            </div>
        </>
    )
}

export default Log
