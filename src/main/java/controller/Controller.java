package controller;

import Model.Information;
import Model.Worker;
import View.GraphicalUserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * The only bridge between the logic in the program and graphical user interface.
 * It can control how many tables are created in GUI and how you can render
 * them. Then it gets information from Model and updates GUI.
 */
public class Controller
{
    private GraphicalUserInterface gui;
    private Information info;
    private final static String CHANNELS = "channels";
    private final static String PROGRAMS = "programs";
    private final static String PROGRAM_DESCRIPTION = "programDescription";
    private final static String PROGRAM_IMAGE = "programImage";
    private Worker worker;

    /**
     * Initiates entire GUI
     */
    public Controller(){
        gui = new GraphicalUserInterface("Radio Info");
        setTimerForWorker();
        createMenuBar();
        addRefreshButtonToMenuBar();
    }

    /**
     * It will make new Worker every hour to keep the information
     * fresh.
     */
    private void setTimerForWorker(){
        Timer t = new Timer(60 * 60 * 1000,this::startWorker);
        t.setInitialDelay(0);
        t.start();
    }

    /**
     * If worker is null, then new worker wil lbe created and
     * executed.
     * @param e ignore
     */
    private void startWorker(ActionEvent e){
        if(worker == null) {
            worker = new Worker(this::initWorker);
            worker.execute();
        }
    }

    /**
     * Is called when Worker is done. It updates the info attribute
     * with new instance of Information.
     */
    private void initWorker() {
        try {
            info = worker.get();
            if(info == null){
                handleException();
            }

            worker = null;
            SwingUtilities.invokeLater(() -> gui.switchOnContent());
        } catch (InterruptedException | ExecutionException e) {
            info = null;
        }
        if(info == null){
            handleException();
        }else {
            setActionListenerForChannels();
            setActionListenerForPrograms();
        }
    }

    /**
     * When something goes wrong when parsing from API.
     */
    private void handleException(){
        gui.displayErrorMessage("Could not load information. Do you want to exit?");
    }

    /**
     * Creates menuBar in GUI.
     */
    private void createMenuBar(){
        gui.createMenuBar();
    }

    /**
     * Adds actionListener for RefreshButton. When pressed
     * it creates and executes Worker.
     */
    private void addRefreshButtonToMenuBar(){
        gui.addMenuToBar("Refresh");
        gui.setListenerForMenu(e -> {
            if(worker == null){
                gui.switchOnContent();
                worker = new Worker(this::initWorker);
                worker.execute();
            }
        }, "Refresh");
    }

    /**
     * Sets listener for channel table. Whenever someone clicks on
     * any channel, program table will be filled with programs
     * that that specific channel has.
     */
    private void setActionListenerForChannels(){
        info.getChannels(objects -> gui.addContent(CHANNELS, objects));
        gui.setListenerOnContent(CHANNELS, channel -> {
            gui.clearContent(PROGRAMS);
            info.getProgramForChannel(objects ->
                    gui.addContent(PROGRAMS, objects), channel);
        }, 1);
    }

    /**
     * Sets listener for program table. Whenever someone clicks on
     * any program, program description table will be filled with
     * new image, title and description for that table.
     */
    private void setActionListenerForPrograms(){
        gui.setListenerOnContent(PROGRAMS, event -> {
            gui.clearContent(PROGRAM_DESCRIPTION);
            info.getProgramDescription(objects -> {
                gui.addContent(PROGRAM_DESCRIPTION, objects);
            }, event);
            info.getProgramImage(objects -> {
                gui.clearContent(PROGRAM_IMAGE);
                gui.addContent(PROGRAM_IMAGE, objects);
            }, event);
        }, 0);
    }
}
