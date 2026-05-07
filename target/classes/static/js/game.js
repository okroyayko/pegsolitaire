let selected = null;
let currentField = null;

function loadState() {
    fetch('/api/game/state')
        .then(r => r.json())
        .then(renderBoard);
}

function renderBoard(field) {
    const boardDiv = document.getElementById('board');
    currentField = field;
    boardDiv.innerHTML = '';

    const tiles = field.tiles;

    for (let r = 0; r < tiles.length; r++) {
        const rowDiv = document.createElement('div');
        rowDiv.className = 'row';

        for (let c = 0; c < tiles[r].length; c++) {
            const tile = tiles[r][c];
            const cell = document.createElement('div');

            cell.className = 'tile ' + tile.state.toLowerCase();
            cell.dataset.row = r;
            cell.dataset.col = c;

            cell.addEventListener('click', onTileClick);

            rowDiv.appendChild(cell);
        }

        boardDiv.appendChild(rowDiv);
    }

    if (field.gameState === "SOLVED") {
        alert("You won!");
    }
    if (field.gameState === "FAILED") {
        alert("No moves left!");
    }
}

function onTileClick(e) {
    const r = parseInt(e.currentTarget.dataset.row);
    const c = parseInt(e.currentTarget.dataset.col);

    document.querySelectorAll('.highlight').forEach(el => el.classList.remove('highlight'));

    if (!selected) {
        selected = { r, c };
        e.currentTarget.classList.add('selected');

        const moves = getPossibleMoves(currentField, r, c);
        moves.forEach(m => {
            const tile = document.querySelector(`[data-row="${m.r}"][data-col="${m.c}"]`);
            tile.classList.add('highlight');
        });

    } else {
        const r2 = r;
        const c2 = c;
        const r1 = selected.r;
        const c1 = selected.c;

        selected = null;

        sendMove(r1, c1, r2, c2);
    }
}

function sendMove(r1, c1, r2, c2) {
    fetch(`/api/game/move?r1=${r1}&c1=${c1}&r2=${r2}&c2=${c2}`, {
        method: 'POST'
    })
        .then(r => r.json())
        .then(renderBoard);
}

function newGame() {
    fetch('/api/game/new', { method: 'POST' })
        .then(r => r.json())
        .then(renderBoard);
}

function changeLevel() {
    const level = document.getElementById("levelSelect").value;

    fetch(`/api/game/new?level=${level}`, {
        method: 'POST'
    })
        .then(r => r.json())
        .then(renderBoard)
        .catch(err => console.error("Level change error:", err));
}

function getPossibleMoves(field, r, c) {
    const moves = [];
    const dirs = [
        {dr: -2, dc: 0},
        {dr: 2, dc: 0},
        {dr: 0, dc: -2},
        {dr: 0, dc: 2}
    ];

    for (const d of dirs) {
        const r2 = r + d.dr;
        const c2 = c + d.dc;

        if (r2 < 0 || r2 >= field.rowCount || c2 < 0 || c2 >= field.columnCount)
            continue;

        const midR = (r + r2) / 2;
        const midC = (c + c2) / 2;

        if (field.tiles[midR][midC].state === "PEG" &&
            field.tiles[r2][c2].state === "EMPTY") {
            moves.push({r: r2, c: c2});
        }
    }

    return moves;
}

document.addEventListener('DOMContentLoaded', loadState);
