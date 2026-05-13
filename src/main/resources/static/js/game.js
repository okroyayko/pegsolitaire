let selected = null;
let currentField = null;

/* ============================
   INITIAL LOAD OF GAME STATE
============================ */
function getPlayerName() {
    const input = document.querySelector("input[name='player']");
    if (!input) return "Anonymous";
    return input.value.trim() || "Anonymous";
}

function loadState() {
    const player = getPlayerName();

    fetch('/api/game/state?player=' + encodeURIComponent(player))
        .then(r => r.json())
        .then(renderBoard)
        .catch(err => console.error("State load error:", err));
}

/* ============================
   RENDER BOARD
============================ */
function renderBoard(field) {
    currentField = field;
    const boardDiv = document.getElementById('board');
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

    if (field.gameState === "SOLVED") showMessage("You won!", "success");
    if (field.gameState === "FAILED") showMessage("No moves left!", "fail");
}

/* ============================
   TILE CLICK LOGIC
============================ */
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
        const r1 = selected.r;
        const c1 = selected.c;
        selected = null;

        sendMove(r1, c1, r, c);
    }
}
function showMessage(text, type = "success") {
    const box = document.querySelector(".modal-content");
    const textEl = document.getElementById("gameMessageText");

    box.classList.remove("success", "fail");
    textEl.classList.remove("success", "fail");

    box.classList.add(type);
    textEl.classList.add(type);

    textEl.innerText = text;
    document.getElementById("gameMessage").style.display = "flex";
}

function closeMessage() {
    document.getElementById("gameMessage").style.display = "none";
}



/* ============================
   SEND MOVE + ANIMATION
============================ */
function sendMove(r1, c1, r2, c2) {
    const fromTile = document.querySelector(`[data-row="${r1}"][data-col="${c1}"]`);

    fromTile.classList.add("moving");

    setTimeout(() => {
        fetch(`/api/game/move?r1=${r1}&c1=${c1}&r2=${r2}&c2=${c2}`, { method: 'POST' })
            .then(r => r.json())
            .then(field => {
                renderBoard(field);

                const newTile = document.querySelector(`[data-row="${r2}"][data-col="${c2}"]`);
                if (newTile) {
                    newTile.classList.add("landed");
                    setTimeout(() => newTile.classList.remove("landed"), 200);
                }
            });
    }, 150);

    setTimeout(() => fromTile.classList.remove("moving"), 300);
}

/* ============================
   NEW GAME
============================ */
function newGame() {
    fetch('/api/game/new', { method: 'POST' })
        .then(r => r.json())
        .then(renderBoard);
}

/* ============================
   CHANGE LEVEL
============================ */
function changeLevel() {
    const level = document.getElementById("levelSelect").value;

    fetch(`/api/game/new?level=${level}`, { method: 'POST' })
        .then(r => {
            if (!r.ok) throw new Error("Level not found");
            return r.json();
        })
        .then(field => {
            renderBoard(field);
            loadState();
        })
        .catch(err => console.error("Level change error:", err));
}

/* ============================
   POSSIBLE MOVES
============================ */
function getPossibleMoves(field, r, c) {
    const moves = [];
    const dirs = [
        { dr: -2, dc: 0 },
        { dr: 2, dc: 0 },
        { dr: 0, dc: -2 },
        { dr: 0, dc: 2 }
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
            moves.push({ r: r2, c: c2 });
        }
    }

    return moves;
}

/* ============================
   DARK MODE + INITIAL LOAD
============================ */
document.addEventListener('DOMContentLoaded', () => {

    const toggle = document.getElementById("themeToggle");

    if (toggle) {
        if (localStorage.getItem("theme") === "dark") {
            document.body.classList.add("dark");
            toggle.textContent = "☀️";
        } else {
            toggle.textContent = "🌙";
        }

        toggle.addEventListener("click", () => {
            document.body.classList.toggle("dark");

            if (document.body.classList.contains("dark")) {
                localStorage.setItem("theme", "dark");
                toggle.textContent = "☀️";
            } else {
                localStorage.setItem("theme", "light");
                toggle.textContent = "🌙";
            }
        });
    }

    loadState();
});
