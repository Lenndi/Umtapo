const {app, BrowserWindow} = require('electron');
const {autoUpdater} = require('electron-updater');
const path = require('path');
const url = require('url');

let mainWindow;

function createWindow () {
    mainWindow = new BrowserWindow({
        width: 1300,
        height: 700,
        minWidth: 1300,
        minHeight: 700,
        center: true,
        title: 'Umtapo'
    });
    mainWindow.loadURL(`file://${__dirname}/index.html`);
    mainWindow.on('closed', () => {
        mainWindow = null
    });
}

app.on('ready', createWindow);

app.on('window-all-closed', function () {
    if (process.platform !== 'darwin') {
        app.quit()
    }
});

app.on('activate', function () {
    if (mainWindow === null) {
        createWindow()
    }
});

autoUpdater.on('update-downloaded', (ev, info) => {
    setTimeout(function () {
        autoUpdater.quitAndInstall();
    }, 5000)
})

app.on('ready', function () {
    autoUpdater.checkForUpdates();
});
