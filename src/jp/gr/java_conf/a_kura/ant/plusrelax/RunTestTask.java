package jp.gr.java_conf.a_kura.ant.plusrelax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.a_kura.ant.plusrelax.engine.RelaxEngine;
import jp.gr.java_conf.a_kura.ant.plusrelax.engine.RelaxEngineFactory;
import jp.gr.java_conf.a_kura.ant.plusrelax.engine.RelaxResult;
import jp.gr.java_conf.a_kura.chatter.ChatterException;
import jp.gr.java_conf.a_kura.chatter.ChatterService;
import jp.gr.java_conf.a_kura.chatter.attachment.Attachment;
import jp.gr.java_conf.a_kura.chatter.attachment.ExistingContentAttachment;
import jp.gr.java_conf.a_kura.chatter.auth.UnauthenticatedException;
import jp.gr.java_conf.a_kura.chatter.message.HashTagSegment;
import jp.gr.java_conf.a_kura.chatter.message.LinkSegment;
import jp.gr.java_conf.a_kura.chatter.message.MentionSegment;
import jp.gr.java_conf.a_kura.chatter.message.Message;
import jp.gr.java_conf.a_kura.chatter.message.TextSegment;

import org.apache.tools.ant.BuildException;

import com.salesforce.ant.SFDCAntTask;
import com.sforce.soap.apex.RunTestsRequest;
import com.sforce.soap.apex.RunTestsResult;
import com.sforce.soap.apex.SoapConnection;

public class RunTestTask extends SFDCAntTask {
    @Override
    public void execute() throws BuildException {
        validateAttributes();

        RunTestsRequest runTests = new RunTestsRequest();
        runTests.setAllTests(getRunAllTests());
        runTests.setClasses(getClasses());

        if (getNamespace() != null) {
            runTests.setNamespace(getNamespace());
        }

        try {
            SoapConnection sc = getApexConnection();
            RunTestsResult testResult = sc.runTests(runTests);
            
            RelaxEngine engine = null;
            if(getIsDebug()) {
            	engine = RelaxEngineFactory.createDebug();
            } else {
            	String engineName = getEngineName();
            	if(engineName == null || engineName.length() == 0) {
            		engine = RelaxEngineFactory.create();
            	} else {
            		engine = RelaxEngineFactory.create(engineName);
            	}
            }
            RelaxResult result = engine.execute(testResult);

            String contentId = null;
    		switch(result.getJudge()) {
    		case RelaxResult.SUCCESS:
    			if(getContentSuccess() != null) {
    				contentId = getContentSuccess();
    			}
    			break;
    		case RelaxResult.SHORT_COVERAGE:
    			if(getContentShortCoverage() != null) {
    				contentId = getContentShortCoverage();
    			}
    			break;
    		case RelaxResult.FAILURE:
    			if(getContentFailure() != null) {
    				contentId = getContentFailure();
    			}
    			break;
    		}
            postChatter(result.getMessage(), contentId);
        } catch (BuildException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }

    private void postChatter(String msg, String contentId) throws IOException, UnauthenticatedException, ChatterException {
		Message message = new Message();

		if(getMentionUserId() != null && getMentionUserId().length() > 0)
			message.addMessageSegment(new MentionSegment(getMentionUserId()));
		message.addMessageSegment(new TextSegment(msg));

		if(getHashTag() != null && getHashTag().length() > 0)
			message.addMessageSegment(new HashTagSegment(getHashTag()));

		Attachment attachment = null;
		if(contentId != null && contentId.length() > 0)
			attachment = new ExistingContentAttachment(contentId);

		ChatterService chatter = new ChatterService(getClientId(), getClientSecret());
		if(getChatServerUrl() != null && getChatServerUrl().length() > 0)
			chatter.setServerUrl(getChatServerUrl());

		chatter.authenticate(getChatUsername(), getChatPassword());
		chatter.post(message, attachment);
    }

    boolean runAllTests = true;
    List<ClassElement> listClass = new ArrayList<ClassElement>();
    String namespace = null;

	String clientId = null;
	String clientSecret = null;
	String chatUsername = null;
	String chatServerUrl = "https://login.salesforce.com";
	String chatPassword = null;
	String mentionUserId = null;
	String hashTag = null;
	String contentSuccess = null;
	String contentFailure = null;
	String contentShortCoverage = null;
	String engineName = null;
	boolean isDebug = false;

    public String[] getClasses() {
        List<String> result = new ArrayList<String>();
        for (ClassElement c : listClass) {
            if (c.getRunTest()) {
            	result.add(c.text);
            }
        }
        return result.toArray(new String[0]);
    }

    public ClassElement createClass() {
    	ClassElement c = new ClassElement();
    	listClass.add(c);
    	return c;
    }

    public String getChatServerUrl() {
    	return chatServerUrl;
    }
    
    public void setChatServerUrl(String chatServerUrl) {
    	this.chatServerUrl = chatServerUrl;
    }

    public String getChatPassword() {
    	return chatPassword;
    }
    
    public void setChatPassword(String chatPassword) {
    	this.chatPassword = chatPassword;
    }

    public String getChatUsername() {
    	return chatUsername;
    }
    
    public void setChatUsername(String chatUsername) {
    	this.chatUsername = chatUsername;
    }

	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
    
	public String getContentSuccess() {
		return contentSuccess;
	}
	
	public void setContentSuccess(String contentSuccess) {
		this.contentSuccess = contentSuccess;
	}
    
	public String getContentFailure() {
		return contentFailure;
	}
	
	public void setContentFailure(String contentFailure) {
		this.contentFailure = contentFailure;
	}
    
	public String getContentShortCoverage() {
		return contentShortCoverage;
	}
	
	public void setContentShortCoverage(String contentShortCoverage) {
		this.contentShortCoverage = contentShortCoverage;
	}

	public String getEngineName() {
		return engineName;
	}
	
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

    public boolean getIsDebug() {
    	return isDebug;
    }
    
    public void setIsDebug(boolean isDebug) {
    	this.isDebug = isDebug;
    }

    public String getHashTag() {
    	return hashTag;
    }
    
    public void setHashTag(String hashTag) {
    	this.hashTag = hashTag;
    }

    public String getMentionUserId() {
    	return mentionUserId;
    }
    
    public void setMentionUserId(String mentionUserId) {
    	this.mentionUserId = mentionUserId; 
    }

    public String getNamespace() {
    	return namespace;
    }
    
    public void setNamespace(String namespace) {
    	this.namespace = namespace;
    }

    public boolean getRunAllTests() {
    	return runAllTests;
    }
    
    public void setRunAllTests(boolean runAllTests) {
    	this.runAllTests = runAllTests;
    }
    
    public class ClassElement {
        private String text = "";
        private boolean runTest = true;
        
        public void addText(String text) {
            this.text += text;
        }

        public String getText() {
            return this.text;
        }

        public boolean getRunTest() {
            return runTest;
        }

        public void setRunTest(boolean runTest) {
            this.runTest = runTest;
        }        
    }
    
}
