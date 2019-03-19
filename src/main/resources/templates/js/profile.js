
var ranks = [];
var wins = [];
var dates = [];
var x = 0;
[# th:each="ranking : ${rankings}"]
var rank = [[${ranking.rank}]];
var preWins = [[${ranking.wins - ranking.losses}]]
    ranks.push({x : + x, y : rank});
    wins.push({x : + x, y : preWins});
    dates.push([[${ranking.date}]]);
    x++;
[/]

var ctx = $('#canvas-rank');
var rankOverTimeGraph = new Chart(ctx, {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: "rgb(0,0,255)",
            data: ranks
        }]
    },
    options: {
        legend: { display: false },
        scales: {
            yAxes: [{
                ticks: {
                    reverse: true,
                }
            }]
        }
    }
});

var cty = $('#canvas-wins');
var winsOverTimeGraph = new Chart(cty, {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: "rgb(0,0,255)",
            data: wins
        }]
    },
    options: {
        legend: { display: false }
    }
});