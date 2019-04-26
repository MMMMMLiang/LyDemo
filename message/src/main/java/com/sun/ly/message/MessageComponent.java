package com.sun.ly.message;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

public class MessageComponent implements IComponent {
    @Override
    public String getName() {
        return "MessageComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        // true: 异步实现
        //    onCall方法执行完成后，CC框架会等待组件在未来的某个时间点调用CC.sendCCResult(callId, result)返回本次组件调用结果
        //false: 同步实现
        //    在return之前调用CC.sendCCResult(callId, result)返回本次组件调用结果，否则系统会返回-10的状态码给调用方
        CC.sendCCResult(cc.getCallId(), CCResult.success("key", new MessageFragment()));
        return false;
    }
}
