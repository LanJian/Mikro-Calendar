package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

public class ReplyView extends JPanel {
	JTextArea textArea;
	JButton submitButton, cancelButton;
	JPanel buttonPanel;
	EventViewManager evm;
 
	public ReplyView(MikroEventModel e, EventViewManager m) {
		super();
		evm = m;
		textArea = new JTextArea("@"+evm.event.getAuthor()+" ", 4,45);
		Font f = new Font("Nimbus Sans L Bold", Font.PLAIN, 15);
		textArea.setFont(f);
		textArea.setLineWrap(true);
		((AbstractDocument) textArea.getDocument()).setDocumentFilter(new DocumentSizeFilter(140));
		buttonPanel = new JPanel();
		submitButton = new JButton("Post");
		cancelButton = new JButton("Cancel");
		
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.reply(textArea.getText());
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.setEventView();
			}
		});
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(submitButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(cancelButton);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(textArea);
		this.add(buttonPanel);
	}

	public void ready() {
		textArea.setText("@"+evm.event.getAuthor()+" ");
		textArea.setCaretPosition(textArea.getText().length());
		textArea.requestFocus();
	}

	public void setBg(Color c) {
		setBackground(c);
		buttonPanel.setBackground(c);
	}

}
