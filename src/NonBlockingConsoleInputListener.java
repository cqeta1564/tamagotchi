class NonBlockingConsoleInputListener implements java.awt.event.KeyListener {

    protected volatile boolean listening = true;

    public void stopListening(Thread mainThread) {
        listening = false;
        mainThread.interrupt();
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        char input = e.getKeyChar();
        System.out.println("Last Key Pressed: " + input);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        // Not used
    }
}