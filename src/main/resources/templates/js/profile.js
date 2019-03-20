// Dates defines the x axis on each chart
var dates = [];
var x = 0;
// Other data to track in each chart (defines y axis)
var ranksOverTime = [];
var diffWinLossesOverTime = [];
var winRateOverTime = [];

// Get data based on user statistics
[# th:each="statistic : ${statistics}"]
    var rank = [[${statistic.rank}]];
    var diffWinLoose = [[${statistic.wins - statistic.losses}]];
    var winRate = [[${statistic.wins / statistic.losses}]];
    ranksOverTime.push({x : + x, y : rank});
    diffWinLossesOverTime.push({x : + x, y : diffWinLoose});
    winRateOverTime.push({x : + x, y : winRate});
    dates.push([[${statistic.date}]]);
    x++;
[/]

var rankOverTimeChart = new Chart($('#canvas-rank-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: "rgb(0,0,255)",
            data: ranksOverTime
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

var winRateOverTimeChart= new Chart($('#canvas-win-rate-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: "rgb(0,0,255)",
            data: winRateOverTime
        }]
    },
    options: {
        legend: { display: false }
    }
});

var winsAndLossesPerDayChart = new Chart($('#canvas-wins-losses-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: "rgb(0,0,255)",
            data: diffWinLossesOverTime
        }]
    },
    options: {
        legend: { display: false }
    }
});

