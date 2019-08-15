package com.delong.web.iweixun.bean.factory;

import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.bean.db.UserFollow;
import com.delong.web.iweixun.utils.Hib;
import com.delong.web.iweixun.utils.TextUtil;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class UserFactory {
    public static User findByToken(String token) {
        return Hib.query(session -> (User) session.createQuery("from User where token =:token")
                .setParameter("token", token)
                .uniqueResult());
    }


    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session.createQuery("from User where phone =:phone")
                .setParameter("phone", phone)
                .uniqueResult());
    }

    public static User findByName(String name) {
        return Hib.query(session -> (User) session.createQuery("from User where name =:name")
                .setParameter("name", name).uniqueResult());
    }

    public static User findById(String userId) {
        return Hib.query(session -> session.get(User.class, userId));
    }

    public static User update(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    public static User login(String account, String password) {
        final String accountStr = account.trim();
        final String encodePassword = encodePassword(password);

        User user = Hib.query(session -> (User) session.createQuery("from User where phone=:phone and password =:password")
                .setParameter("phone", accountStr)
                .setParameter("password", encodePassword)
                .uniqueResult()
        );

        if (user != null) {
            user = login(user);
        }

        return user;

    }

    public static User register(String account, String name, String password) {
        account = account.trim();
        password = encodePassword(password);

        User user = createUser(account, name, password);
        if (user != null) {
            user = login(user);
        }
        return user;
    }

    private static User login(User user) {
        String newToken = UUID.randomUUID().toString();
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return update(user);
    }

    private static final User createUser(String account, String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPhone(account);
        user.setPassword(password);
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    private static String encodePassword(String password) {
        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }

    public static List<User> contacts(User self) {
        return Hib.query(session -> {

            session.load(self, self.getId());
            Set<UserFollow> flows = self.getFollowing();
            return flows.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());

        });

    }


    public static User follow(final User origin, final User target, String alias) {
        UserFollow follow = getUserFollow(origin, target);
        if (follow != null) {
            return follow.getTarget();
        }

        return Hib.query(session -> {
            session.load(origin, origin.getId());
            session.load(target, target.getId());
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);

            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });

    }

    public static UserFollow getUserFollow(final User origin, final User target) {
        return Hib.query(session -> (UserFollow) session.createQuery("from UserFollow where originId =:originId and targetId =:targetId")
                .setParameter("originId", origin.getId())
                .setParameter("targetId", target.getId())
                .setMaxResults(1)
                .uniqueResult());

    }

    public static User bindPushId(User user, String pushId) {
        Hib.queryOnly(session -> {
            List<User> userList = (List<User>) session
                    .createQuery("from User where lower(pushId) =: pushId and id !=:userId")
                    .setParameter("userId", user.getId())
                    .setParameter("pushId", pushId.toLowerCase())
                    .list();
            for (User u : userList) {
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(user.getPushId())) {
            return user;
        } else {
            if (!Strings.isNullOrEmpty(user.getPushId())) {
                //TODO
            }
            user.setPushId(pushId);

            return update(user);
        }

    }

    public static List<User> search(String name) {
        if (Strings.isNullOrEmpty(name))
            name = "";
        String searchName = "%" + name + "%";

        return Hib.query(session -> {
            return (List<User>)session.createQuery("from User where lower(name) like :name and portrait is not null and description is not null ")
                    .setParameter("name", searchName)
                    .setMaxResults(20)
                    .list();

        });
    }
}
