
package groovy.com.org.groovyexec

import groovy.swing.SwingBuilder


/**
 * Created by alexmoldovan on 12/12/2016.
 */


import groovy.text.SimpleTemplateEngine

import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JTextArea

public class GroovyExec{
    static def inputText;

    public static void main(String [] args){
        println "Starting tool...!"
        gui()
    }

    public static gui(){
        groovy.swing.SwingBuilder.build {
            root = frame(title:'Example Frame', show:true, pack:true) {

                menuBar {
                    menu(text:'Help') {
                        menuItem() {
                            action(name:'Just kidding, no help here')
                        }
                    }
                }

                tableLayout {
                    tr {
                        td {
                            label(text:'Example Input')
                        }
                        td(colfill:true) {
                            inputTextElement = textField(columns:15)
                        }
                    }
                    tr {
                        td(colspan:2, align:'center') {
                            button('Generate',
                                    actionPerformed: {

                                        if(verifyEmpty(inputTextElement)){
                                            inputText = inputTextElement.text.trim()
                                        }

                                        // PROCESS and SHOW
                                        if(inputText){
                                            // process
                                            def fileContent = readFileContent()

                                            JTextArea textAreaFile = new JTextArea(10, 50);
                                            textAreaFile.setText(fileContent);
                                            textAreaFile.setEditable(false);

                                            JTextArea textAreaText = new JTextArea(10, 50);
                                            textAreaText.setText(inputText);
                                            textAreaText.setEditable(false);

                                            // wrap a scrollpane around it
                                            JScrollPane scrollPane1 = new JScrollPane(textAreaFile);
                                            JScrollPane scrollPane2 = new JScrollPane(textAreaText);

                                            // display them in a message dialog
                                            JOptionPane.showMessageDialog(root, scrollPane1, "File Contents", JOptionPane.INFORMATION_MESSAGE);
                                            JOptionPane.showMessageDialog(root, scrollPane2, "Box2", JOptionPane.INFORMATION_MESSAGE);

                                        }else{
                                            optionPane().showMessageDialog(root, "Empty input text!")
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

    public static boolean verifyEmpty(JComponent input){
        def textField = input.text.trim()

        // could be changed to return textField?.trim()
        if (textField?.trim()) {
            return true
        }else{
            return false
        }
    }

    public static String readFileContent(){
        String fileContent = readFile("file.txt")
        return fileContent
    }

    private static String readFile(String path){
        InputStream file = this.getClass().getResourceAsStream( "/"+ path )
        return file.text
    }
}
